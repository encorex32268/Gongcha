package com.lihan.auth.presentation.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.lihan.auth.presentation.manager.GoogleAuthManager
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.domain.model.User
import com.lihan.core.domain.util.Result
import com.lihan.core.domain.util.onError
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.presentation.ui.BuildConfig
import com.lihan.core.presentation.ui.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val googleAuthManager: GoogleAuthManager,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<LoginEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** init */
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
            LoginAction.OnWithoutSigningClick -> signInAnonymously()
            is LoginAction.OnContinueClick -> Unit
            is LoginAction.OnGoogleSignIn -> googleSignIn(action.activity)
        }
    }

    private fun signInAnonymously() {
        viewModelScope.launch {
            val result = googleAuthManager.signInAnonymously()
            result
                .onSuccess {
                    handleGoogleUser(it)
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun googleSignIn(activity: Activity) {
        viewModelScope.launch {

            val result = googleAuthManager.signIn(
                activity = activity,
                webClientId = BuildConfig.WEB_CLIENT_ID
            )

            result.onSuccess { googleUser ->
                handleGoogleUser(googleUser)
            }.onError { error ->
                _state.update {
                    it.copy(
                        isVerifying = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
        }

    }

    private suspend fun handleGoogleUser(googleUser: FirebaseUser) {
        val result = authRepository.getUserById(googleUser.uid)
        var user: User? = null
        when (result) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        isVerifying = false,
                        errorMessage = result.error.toUiText()
                    )
                }
            }

            is Result.Success -> {
                user = result.data
            }
        }

        if (user == null) {
            val createdUser = User(
                id = googleUser.uid,
                level = MemberLevel.COPPER,
                name = googleUser.displayName ?: googleUser.email ?: "",
                imageUrl = googleUser.photoUrl?.toString(),
                points = 0,
                memberCode = "KC" + googleUser.uid
            )
            authRepository
                .createUser(createdUser)
                .onError { error ->
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
                .onSuccess {
                    _state.update { it.copy(isVerifying = false) }
                    // 登入成功，導航至首頁/Menu
                    _uiEvent.send(LoginEvent.OnNavigateToMenu)
                }
        } else {
            _state.update { it.copy(isVerifying = false) }
            // 登入成功，導航至首頁/Menu
            _uiEvent.send(LoginEvent.OnNavigateToMenu)
        }
    }
}