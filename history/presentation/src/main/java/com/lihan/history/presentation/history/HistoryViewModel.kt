package com.lihan.history.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.util.onError
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.presentation.ui.toUiText
import com.lihan.history.domain.HistoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private var syncJob: Job? = null

    private val _uiEvent = Channel<HistoryEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeHistories()
                observeUser()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryState()
        )

    fun onAction(action: HistoryAction) {
        when (action) {
            HistoryAction.OnSyncHistories -> syncHistories()
            is HistoryAction.OnOrderCancelClick -> {
                _state.update { it.copy(
                    selectedOrderId = action.orderId,
                    isShowCancelOrderDialog = true
                ) }
            }
            HistoryAction.OnDismissCancelOrderDialog -> {
                _state.update { it.copy(
                    isShowCancelOrderDialog = false,
                    selectedOrderId = null
                ) }
            }
            HistoryAction.OnConfirmCancelOrderClick -> cancelOrder()

        }
    }

    private fun cancelOrder(){
        val orderId = _state.value.selectedOrderId
        if (_state.value.isSyncing || orderId == null) {
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(
                isShowCancelOrderDialog = false,
                selectedOrderId = null,
                isSyncing = true
            ) }
            //Animation delay
            delay(300L.milliseconds)
            historyRepository.cancelOrder(orderId)
                .onSuccess {
                    syncHistories()
                    _uiEvent.send(HistoryEvent.OnCancelOrderSuccess)
                }
                .onError { error ->
                    _uiEvent.send(HistoryEvent.OnCancelOrderFailed(error.toUiText()))
                }

            _state.update { it.copy(
                isSyncing = false
            ) }
        }
    }


    //It is called in `onResume` because the order status needs to be checked frequently.
    private fun syncHistories(){
        if (syncJob?.isActive == true) return

        syncJob = viewModelScope.launch {
            historyRepository.syncHistories()
        }

    }

    private fun observeHistories(){
        historyRepository
            .histories
            .onEach { histories ->
                val sortedHistories = histories.sortedByDescending { it.createAt }

                _state.update { it.copy(
                    items = sortedHistories
                ) }
            }.launchIn(viewModelScope)
    }

    private fun observeUser() {
        authRepository
            .user
            .onEach {  user ->
                _state.update { it.copy(
                    isLogin = user != null
                ) }
            }.launchIn(viewModelScope)
    }

}