package com.lihan.member.presentation.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberViewModel(
    private val appDataStore: AppDataStore,
    private val authRepository: AuthRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MemberState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            /** init */
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MemberState()
        )

    init {
        appDataStore
            .user
            .onEach { user ->
                _state.update { it.copy(
                    user = user
                ) }
            }.launchIn(viewModelScope)

    }

    fun onAction(action: MemberAction) {
        when (action) {
            else -> Unit
        }
    }
}