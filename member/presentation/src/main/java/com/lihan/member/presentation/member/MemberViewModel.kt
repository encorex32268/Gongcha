package com.lihan.member.presentation.member

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.member.presentation.member.util.QRCodeGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberViewModel(
    private val authRepository: AuthRepository,
    private val qrCodeGenerator: QRCodeGenerator
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MemberState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeUser()
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MemberState()
        )

    fun onAction(action: MemberAction) {
        when (action) {
            MemberAction.OnLoginClick -> Unit
            MemberAction.OnShowQRCode -> showQRCode()
            MemberAction.OnDismissQRCode -> dismissQRCode()
        }
    }

    private fun observeUser(){
        authRepository
            .user
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onEach { user ->
                _state.update { it.copy(
                    user = user,
                    qrCode = createQRCode(user?.memberCode),
                    isLoading = false
                ) }
            }
            .catch { _state.update { it.copy(isLoading = false) } }
            .launchIn(viewModelScope)
    }

    private suspend fun createQRCode(memberCode: String?): Bitmap?{
        if (memberCode == null) return null
        return qrCodeGenerator.generate(memberCode)
    }

    private fun showQRCode(){
        _state.update { it.copy(
            isShowQRCode = true
        ) }
    }

    private fun dismissQRCode(){
        _state.update { it.copy(
            isShowQRCode = false
        ) }
    }
}