package com.lihan.menu.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.CartRepository
import com.lihan.menu.domain.MenuRepository
import com.lihan.menu.menu.components.TeaType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val appDataStore: AppDataStore
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

        viewModelScope.launch {
            menuRepository.syncTeasData()
        }

    }

    fun onAction(action: MenuAction) {
        when (action) {
            MenuAction.OnSelectStore -> {}
            is MenuAction.OnTeaClick -> {
                viewModelScope.launch {
                    if (_state.value.store == null){
                        _uiEvent.send(MenuEvent.OnSendStorePickWarning)
                        return@launch
                    }
                    _uiEvent.send(MenuEvent.OnNavigateToTeaOrder(action.tea.id))
                }
            }
            is MenuAction.OnTeaTypeSelect -> {
                _state.update { it.copy(
                    type = action.type
                ) }
            }
            MenuAction.OnNavigateToMember -> {}
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
}