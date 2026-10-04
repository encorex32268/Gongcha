package com.lihan.menu.tea_order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.GongchaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeaOrderViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: GongchaRepository
) : ViewModel() {

    private val teaId: String? = savedStateHandle.get<String>("teaId")
    private var hasLoadedInitialData = false

    private val initialTea = teaId?.let { repository.getCachedTea(it) }

    private val _state = MutableStateFlow(
        TeaOrderState(
            tea = initialTea,
            isLoading = initialTea == null
        )
    )
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeTea()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TeaOrderState(
                tea = initialTea,
                isLoading = initialTea == null
            )
        )

    fun onAction(action: TeaOrderAction) {
        when (action) {
            TeaOrderAction.OnNavigateBack -> Unit
        }
    }

    private fun observeTea() {
        if (teaId == null) {
            _state.update { it.copy(isLoading = false) }
            return
        }
        viewModelScope.launch {
            val tea = repository.getTeaById(teaId)
            if (tea != null) {
                _state.update { it.copy(tea = tea, isLoading = false) }
            } else {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}