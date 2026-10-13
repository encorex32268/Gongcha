@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.menu.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.model.Tea
import com.lihan.menu.domain.MenuRepository
import com.lihan.menu.presentation.menu.components.TeaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<MenuEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _selectedType = MutableStateFlow(TeaType.ALL)

    private val _state = MutableStateFlow(MenuState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                viewModelScope.launch {
                    menuRepository.syncTeasData()
                }
                observeData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MenuState()
        )

    private fun observeData() {
        val canSelectStoreFlow = authRepository.user.flatMapLatest { user ->
            if (user == null) flowOf(true)
            else cartRepository.getCarItemsCountByUserId(user.id).map { it == 0 }
        }

        combine(
            menuRepository.teas,
            menuRepository.selectedStore,
            authRepository.user,
            _selectedType,
            canSelectStoreFlow
        ) { teas, store, user, selectedType, canSelectStore ->
            val filteredTeas = when (selectedType) {
                TeaType.ALL -> teas
                else -> teas.filter { it.type == selectedType.name }
            }
            _state.update { current ->
                current.copy(
                    userName = user?.name,
                    profileImageUrl = user?.imageUrl,
                    store = store,
                    teas = filteredTeas,
                    type = selectedType,
                    canSelectStore = canSelectStore
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: MenuAction) {
        when (action) {
            is MenuAction.OnTeaTypeSelect -> {
                _selectedType.value = action.type
            }
            MenuAction.OnShowSignOutDialog -> {
                _state.update { it.copy(isShowSignOutDialog = true) }
            }
            MenuAction.OnDismissSignOutDialog -> {
                _state.update { it.copy(isShowSignOutDialog = false) }
            }
            MenuAction.OnShowSelectStoreWarningDialog -> {
                _state.update { it.copy(isShowSelectStoreWarning = true) }
            }
            MenuAction.OnDismissSelectStoreWarningDialog -> {
                _state.update { it.copy(isShowSelectStoreWarning = false) }
            }
            MenuAction.OnSelectStore -> navigateToSelectStore()
            is MenuAction.OnTeaClick -> onTeaClick(action.tea)
            MenuAction.OnSignOutDialogConfirmClick -> confirmSignOut()
            MenuAction.OnNavigateToMember -> Unit
        }
    }

    private fun navigateToSelectStore() {
        if (!_state.value.canSelectStore) {
            _state.update { it.copy(isShowSelectStoreWarning = true) }
        } else {
            viewModelScope.launch {
                _uiEvent.send(MenuEvent.OnNavigateToSelectStore)
            }
        }
    }

    private fun onTeaClick(tea: Tea) {
        viewModelScope.launch {
            if (_state.value.store == null) {
                _uiEvent.send(MenuEvent.OnSendStorePickWarning)
                return@launch
            }
            _uiEvent.send(MenuEvent.OnNavigateToTeaOrder(tea.id))
        }
    }

    private fun confirmSignOut() {
        viewModelScope.launch {
            _uiEvent.send(MenuEvent.OnSignOut)
        }
    }
}