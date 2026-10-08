package com.lihan.auth.verify_phonenumber

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAuthManager{

    private val auth =  FirebaseAuth.getInstance()
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken?=null

    fun sendPhoneNumber(
        activity: Activity,
        phoneNumber: String,
        onCodeSent: () -> Unit,
        onAutoVerifiedSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //Has Credential
                signInWithPhoneCredential(
                    activity = activity,
                    credential = credential,
                    onAutoVerifiedSuccess,
                    onError
                )
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onError(e.localizedMessage?:"")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token
                onCodeSent()
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .apply {
                // 如果是重新發送驗證碼，可帶入 resendToken
                resendToken?.let { setForceResendingToken(it) }
            }
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(
        activity: Activity,
        code: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val verificationId = storedVerificationId ?: run {
            onError("Verification ID 為空，請重新發送驗證碼")
            return
        }
        // 用 verificationId 和使用者輸入的簡訊驗證碼建立 Credential
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        signInWithPhoneCredential(activity,credential, onSuccess, onError)
    }



    private fun signInWithPhoneCredential(
        activity: Activity,
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidCredentialsException) {
                        // 這裡可以透過 exception.errorCode 判斷
                        when (exception.errorCode) {
                            "ERROR_SESSION_EXPIRED" -> {
                                onError("驗證碼已過期，請重新發送")
                            }
                            "ERROR_INVALID_VERIFICATION_CODE" -> {
                                onError("驗證碼錯誤，請重新輸入")
                            }
                            else -> {
                                onError(exception.localizedMessage ?: "驗證碼無效")
                            }
                        }
                    } else {
                        onError(exception?.localizedMessage ?: "登入失敗")
                    }
                }
            }
    }


}