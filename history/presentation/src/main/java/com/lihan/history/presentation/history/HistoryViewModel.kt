package com.lihan.history.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.history.domain.HistoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private var syncJob: Job? = null

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeHistories()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryState()
        )

    init {
        authRepository
            .user
            .onEach {  user ->
                _state.update { it.copy(
                    isLogin = user != null
                ) }
            }.launchIn(viewModelScope)
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            HistoryAction.OnSyncHistories -> syncHistories()
            else -> Unit
        }
    }

    private fun syncHistories(){
        if (syncJob?.isActive == true) return

        syncJob = viewModelScope.launch {
            historyRepository.syncHistories()
        }

    }

    private fun observeHistories(){
        historyRepository
            .getHistories()
            .onEach { histories ->
                val sortedHistories = histories.sortedByDescending { it.createAt }

                _state.update { it.copy(
                    items = sortedHistories
                ) }
            }.launchIn(viewModelScope)
    }

}