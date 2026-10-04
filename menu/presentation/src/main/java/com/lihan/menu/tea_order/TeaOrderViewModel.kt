package com.lihan.menu.tea_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class TeaOrderViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(TeaOrderState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            /** init */
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TeaOrderState()
        )

    fun onAction(action: TeaOrderAction) {
        when (action) {
            else -> Unit
        }
    }
}