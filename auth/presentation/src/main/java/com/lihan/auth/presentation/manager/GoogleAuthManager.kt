package com.lihan.auth.presentation.manager

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

class GoogleAuthManager {

    private val auth = FirebaseAuth.getInstance()

    /**
     * 啟動 Google 登入流程
     * @param activity 供 Credential Manager 彈出 Bottom Sheet 視窗
     * @param webClientId Firebase 後台取得的 Web Client ID
     * @return 登入結果 Result<FirebaseUser>
     */
    suspend fun signIn(
        activity: Activity,
        webClientId: String
    ): Result<FirebaseUser, DataError> {
        Timber.d("FirebaseAuth SignIn")
        val credentialManager = CredentialManager.create(activity)
        // 1. 設定 Google ID 請求選項
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // false 會顯示裝置上的所有帳號，讓使用者可挑選
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(true) // 是否自動選取上次登入的帳號
            .build()

        // 2. 建立 Credential Request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        return try {

            // 3. 呼叫 Credential Manager 彈出選擇介面 (非同步掛起)
            val response = credentialManager.getCredential(
                request = request,
                context = activity
            )
            // 4. 解析取得 Google ID Token
            val credential = response.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                // 5. 透過 ID Token 登入 Firebase
                signInWithFirebase(activity, idToken)

            } else {
                Result.Error(DataError.Firebase.NOT_SUPPORT_CREDENTIAL)
            }
        } catch (e: GetCredentialCancellationException) {
            Timber.d("FirebaseAuth SignIn GetCredentialCancellationException: ${e.localizedMessage}")
            Timber.d("FirebaseAuth User cancel signIn not exception")
            e.printStackTrace()
            // 使用者主動取消/滑掉 Bottom Sheet，不算系統錯誤
//            Result.failure(Exception("使用者已取消登入"))
            Result.Error(DataError.Firebase.GET_SIGNING_CANCELLED)
        } catch (e: GetCredentialException) {
            Timber.d("FirebaseAuth SignIn GetCredentialException: ${e.localizedMessage}")
            e.printStackTrace()
            Result.Error(DataError.Firebase.GET_CREDENTIAL_FAILED)
        } catch (e: Exception) {
            Timber.d("FirebaseAuth SignIn Exception: ${e.localizedMessage}")
            e.printStackTrace()
            Result.Error(DataError.Firebase.UNKNOWN)
        }
    }


    suspend fun signInAnonymously(): Result<FirebaseUser, DataError> = suspendCancellableCoroutine { continuation ->
        Timber.d("FirebaseAuth SignInAnonymously")
        FirebaseAuth.getInstance()
            .signInAnonymously()
            .addOnCompleteListener{
                if (it.isSuccessful){
                    if (continuation.isActive){
                        continuation.resume(Result.Success(it.result.user!!))
                    }
                }
            }
            .addOnFailureListener {
                Timber.d("FirebaseAuth SignInAnonymously Failed ${it.localizedMessage}")
                continuation.resume(Result.Error(DataError.Firebase.SIGNING_FAILED))
            }

        continuation.invokeOnCancellation {
            Timber.d("FirebaseAuth SignInAnonymously Canceled ${it?.localizedMessage}")
            continuation.resume(Result.Error(DataError.Firebase.CANCELED))
        }
    }



    /**
     * 將 Google Token 換取 Firebase Auth 登入狀態
     */
    private suspend fun signInWithFirebase(
        activity: Activity,
        idToken: String
    ): Result<FirebaseUser, DataError> = suspendCancellableCoroutine { continuation ->
        Timber.d("FirebaseAuth SignInWithFirebase")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        Timber.d("FirebaseAuth SignInWithFirebase Successful User: $user")
                        continuation.resume(Result.Success(user))
                    } else {
                        Timber.d("FirebaseAuth SignInWithFirebase Successful User: UNKNOWN")
                        continuation.resume(Result.Error(DataError.Firebase.UNKNOWN_USER))
                    }
                } else {
                    Timber.d("FirebaseAuth SignInWithFirebase Failure Signing Failed")
                    continuation.resume(
                        Result.Error(DataError.Firebase.SIGNING_FAILED)
                    )
                }
            }
    }
}
