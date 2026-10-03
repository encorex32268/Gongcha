package com.lihan.menu.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.GongchaRepository
import com.lihan.core.domain.util.onError
import com.lihan.core.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MenuViewModel(
    private val repository: GongchaRepository
): ViewModel() {

    private var hasLoadedInitialData = false

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
            is MenuAction.OnTeaClick -> {}
            is MenuAction.OnTeaTypeSelect -> {}
        }
    }

    private fun observeTeas(){
        repository
            .getTeas()
            .onEach { result ->
                result.onError {
                    //
                }.onSuccess { teas ->
                    _state.update { it.copy(
                        teas = teas
                    ) }
                }
            }
            .launchIn(viewModelScope)
    }
}