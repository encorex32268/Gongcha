package com.lihan.auth.presentation.login

import android.app.Activity
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.auth.domain.AuthRepository
import com.lihan.auth.domain.PhoneNumberValidator
import com.lihan.auth.presentation.manager.GoogleAuthManager
import com.lihan.auth.presentation.manager.PhoneAuthManager
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.domain.model.User
import com.lihan.core.presentation.ui.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val googleAuthManager: GoogleAuthManager,
    private val authRepository: AuthRepository,
): ViewModel() {

    private var hasLoadedInitialData = false

    private val phoneNumberTextFlow = snapshotFlow { _state.value.textFieldState.text.toString() }

    private val _uiEvent = Channel<LoginEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observePhoneNumberText()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
//            is LoginAction.OnContinueClick -> onContinueClick(action.activity)
            LoginAction.OnWithoutSigningClick -> Unit
            is LoginAction.OnContinueClick -> Unit
            is LoginAction.OnGoogleSignIn -> googleSignIn(action.activity)
        }
    }

    private fun googleSignIn(activity: Activity){
        viewModelScope.launch {

            val webClientId = "148497736151-28j91ni5hsmi0o14omt0aiphb3ubh7vo.apps.googleusercontent.com"
            val result = googleAuthManager.signIn(activity, webClientId)
            result.onSuccess { user ->

                authRepository.setUser(
                    User(
                        level = MemberLevel.NONE,
                        name = user.displayName?:user.email?:"",
                        imageUrl = user.photoUrl?.toString(),
                        points = 0,
                        memberCode = "KC" + user.uid
                    )
                )

                _state.update { it.copy(isVerifying = false) }
                // 登入成功，導航至首頁/Menu
                _uiEvent.send(LoginEvent.OnNavigateToMenu)
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isVerifying = false,
                        errorMessage = UiText.DynamicString(error.message ?: "Google 登入失敗")
                    )
                }
            }
        }

    }

//    private fun onContinueClick(activity: Activity){
//
//        val phoneNumber = _state.value.textFieldState.text.toString()
//        val formatedPhoneNumber = formatToE164(phoneNumber)
//        _state.update { it.copy(
//            isVerifying = true,
//            errorMessage = null
//        ) }
//        //Save User PhoneNumber To DataStore
//        viewModelScope.launch {
//            authRepository.setPhoneNumber(formatedPhoneNumber)
//        }
//
//        //Send PhoneNumber To Firebase
//        phoneAuthManager
//            .sendPhoneNumber(
//                activity = activity,
//                phoneNumber = formatedPhoneNumber,
//                onError = { error ->
//                    _state.update { it.copy(
//                        errorMessage = UiText.DynamicString(error),
//                        isVerifying = false
//                    ) }
//                },
//                onAutoVerifiedSuccess = {
//                    _state.update { it.copy(
//                        errorMessage = null,
//                        isVerifying = false
//                    ) }
//                    viewModelScope.launch {
//                        _uiEvent.send(LoginEvent.OnNavigateToMenu)
//                    }
//                },
//                onCodeSent = {
//                    _state.update { it.copy(
//                        errorMessage = null,
//                        isVerifying = false
//                    ) }
//                    viewModelScope.launch {
//                        _uiEvent.send(LoginEvent.OnNavigateToCodeVerify)
//                    }
//                }
//            )
//
//    }

    private fun observePhoneNumberText(){
        phoneNumberTextFlow
            .onEach { phoneNumberText ->
                _state.update { it.copy(
                    isPhoneNumberValid = PhoneNumberValidator.isValidTaiwanPhone(phoneNumberText)
                ) }

            }.launchIn(viewModelScope)
    }

    private fun formatToE164(phone: String, countryCode: String = "+886"): String {
        val trimmed = phone.trim()
        return if (trimmed.startsWith("0")) {
            countryCode + trimmed.substring(1)
        } else if (!trimmed.startsWith("+")) {
            countryCode + trimmed
        } else {
            trimmed
        }
    }

}