package com.lihan.menu.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.GongchaRepository
import com.lihan.core.domain.util.onError
import com.lihan.core.domain.util.onSuccess
import com.lihan.menu.presentation.menu.components.TeaType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repository: GongchaRepository
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

    fun onAction(action: MenuAction) {
        when (action) {
            MenuAction.OnSelectStore -> {}
            is MenuAction.OnTeaClick -> {
                viewModelScope.launch {
                    if (_state.value.store == null){
                        _uiEvent.send(MenuEvent.OnSendStorePickWarning)
                        return@launch
                    }
                }
            }
            is MenuAction.OnTeaTypeSelect -> {
                _state.update { it.copy(
                    type = action.type
                ) }
            }
        }
    }

    private fun observeTeas(){
        combine(
            state.map { it.type },
            repository.getTeas()
        ){ type , result ->
            result.onError {
                //TODO: Handle Error
            }.onSuccess { teas ->
                val filterTeas = when(type){
                    TeaType.ALL -> teas
                    else -> teas.filter { it.type == type.name }
                }
                _state.update { it.copy(
                    teas = filterTeas
                ) }
            }

        }.launchIn(viewModelScope)
    }
}