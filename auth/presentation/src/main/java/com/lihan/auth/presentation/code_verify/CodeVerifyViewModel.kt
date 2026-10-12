@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.auth.presentation.code_verify

import android.app.Activity
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.auth.presentation.manager.PhoneAuthManager
import com.lihan.core.domain.util.TimerFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CodeVerifyViewModel(
    private val phoneAuthManager: PhoneAuthManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<CodeVerifyEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(CodeVerifyState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                timerFlow()
                observeCodeVerifyTextFields()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CodeVerifyState()
        )

    fun onAction(action: CodeVerifyAction) {
        when (action) {
            is CodeVerifyAction.OnConfirmClick -> onConfirmClick(action.activity)
            CodeVerifyAction.OnWithoutSigningClick -> Unit
            is CodeVerifyAction.OnResendSMSCode -> resendSMSCode(action.activity)
        }
    }

    private fun resendSMSCode(activity: Activity?) {
//        if (activity == null) {
//            return
//        }
//        _state.update {
//            it.copy(
//                isVerifyCodeTimeout = false
//            )
//        }
//        viewModelScope.launch {
//            val phoneNumber = authRepository.phoneNumber.first()
//            if (phoneNumber.isBlank()) {
//                return@launch
//            }
//            phoneAuthManager
//                .sendPhoneNumber(
//                    activity = activity,
//                    phoneNumber = phoneNumber,
//                    onError = { error ->
//                        _state.update {
//                            it.copy(
//                                isVerifyCodeTimeout = true
//                            )
//                        }
//                    },
//                    onCodeSent = {
//                        _state.update {
//                            it.copy(
//                                isVerifyCodeTimeout = false,
//                                isCountDownTimer = true,
//                                timer = 60.seconds
//                            )
//                        }
//                    },
//                    onAutoVerifiedSuccess = { /*Nothing*/ }
//                )
//
//        }

    }

    private fun onConfirmClick(activity: Activity?) {
        if (activity == null) {
            return
        }
        val code = state.value.textFieldStates.joinToString("") { it.text }

        phoneAuthManager.verifyCode(
            activity = activity,
            code = code,
            onSuccess = {
                viewModelScope.launch {
                    _uiEvent.send(CodeVerifyEvent.OnNavigateToMenu)
                }
            },
            onError = {

            }
        )
    }

    private fun timerFlow() {
        TimerFlow
            .timeAndEmit()
            .flatMapLatest {
                if (state.value.isCountDownTimer) {
                    flow { emit(it) }
                } else emptyFlow()
            }.onEach { duration ->
                val newTime = state.value.timer - duration
                if (newTime <= Duration.ZERO) {
                    _state.update {
                        it.copy(
                            timer = 0.seconds,
                            isCountDownTimer = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            timer = newTime
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun observeCodeVerifyTextFields() {
        snapshotFlow {
            _state.value.textFieldStates.map { it.text.toString() }
        }
            .onEach { textList ->
                val fullCode = textList.joinToString("")
                val isAllFilled = textList.all { it.isNotBlank() } // 6格是否都已填滿

                _state.update { it.copy(
                    isValid = isAllFilled
                ) }
            }
            .launchIn(viewModelScope)
    }

}