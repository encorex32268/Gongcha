@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.menu.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import com.lihan.menu.domain.MenuRepository
import com.lihan.menu.presentation.menu.MenuEvent.*
import com.lihan.menu.presentation.menu.components.TeaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val appDataStore: AppDataStore,
    private val cartRepository: CartRepository,
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<MenuEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(MenuState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeTeas()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MenuState()
        )

    init {
        observeSelectedStore()
        observeUser()
        observeCartItem()

        viewModelScope.launch {
            menuRepository.syncTeasData()
        }
    }

    fun onAction(action: MenuAction) {
        when (action) {
            MenuAction.OnSelectStore -> navigateToSelectStore()
            MenuAction.OnNavigateToMember -> Unit
            is MenuAction.OnTeaClick -> {
                viewModelScope.launch {
                    if (_state.value.store == null){
                        _uiEvent.send(MenuEvent.OnSendStorePickWarning)
                        return@launch
                    }
                    _uiEvent.send(OnNavigateToTeaOrder(action.tea.id))
                }
            }
            is MenuAction.OnTeaTypeSelect -> {
                _state.update { it.copy(
                    type = action.type
                ) }
            }
            MenuAction.OnDismissSignOutDialog -> dismissSignOutDialog()
            MenuAction.OnShowSignOutDialog -> showSignOutDialog()
            MenuAction.OnSignOutDialogConfirmClick -> confirmSignOut()
            MenuAction.OnDismissSelectStoreWarningDialog -> dismissSelectStoreWarningDialog()
            MenuAction.OnShowSelectStoreWarningDialog -> showSelectStoreWarningDialog()
        }
    }

    private fun dismissSelectStoreWarningDialog(){
        _state.update { it.copy(
            isShowSelectStoreWarning = false
        ) }
    }
    private fun showSelectStoreWarningDialog(){
        _state.update { it.copy(
            isShowSelectStoreWarning = true
        ) }
    }

    private fun navigateToSelectStore(){
        val canSelectStore = _state.value.canSelectStore
        if (!canSelectStore){
            _state.update { it.copy(
                isShowSelectStoreWarning = true
            ) }
        }else{
            viewModelScope.launch {
                _uiEvent.send(MenuEvent.OnNavigateToSelectStore)
            }
        }
    }

    private fun showSignOutDialog(){
        _state.update { it.copy(
            isShowSignOutDialog = true
        ) }
    }
    private fun dismissSignOutDialog(){
        _state.update { it.copy(
            isShowSignOutDialog = false
        ) }
    }
    private fun confirmSignOut(){
        viewModelScope.launch {
            _uiEvent.send(MenuEvent.OnSignOut)
        }
    }

    private fun observeTeas(){
        combine(
            state.map { it.type },
            menuRepository.getTeas()
        ){ type , teas ->

            val filterTeas = when(type){
                TeaType.ALL -> teas
                else -> teas.filter { it.type == type.name }
            }
            _state.update { it.copy(
                teas = filterTeas
            ) }

        }.launchIn(viewModelScope)
    }

    private fun observeSelectedStore(){
        appDataStore
            .storeId
            .distinctUntilChanged()
            .onEach { storeId ->
                val selectedStore = menuRepository.getLatestSelectedStore(storeId)
                _state.update { it.copy(
                    store = selectedStore
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeUser(){
        appDataStore
            .user
            .mapNotNull { it }
            .onEach { user ->
                _state.update { it.copy(
                    userName = user.name,
                    profileImageUrl = user.imageUrl
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeCartItem(){
        appDataStore.user
            .flatMapLatest { user ->
                if (user == null){
                    emptyFlow()
                }else{
                    cartRepository.getCarItemsCountByUserId(userId = user.id)
                }
            }.onEach { cartCount ->
                _state.update { it.copy(
                    canSelectStore = cartCount == 0
                ) }
            }.launchIn(viewModelScope)

    }
}