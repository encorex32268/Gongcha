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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GoogleAuthManager {

    private val auth = FirebaseAuth.getInstance()

    /**
     * 啟動 Google 登入流程
     * @param activity 供 Credential Manager 彈出 Bottom Sheet 視窗
     * @param webClientId Firebase 後台取得的 Web Client ID
     * @return 登入結果 Result<FirebaseUser>
     */
    //TODO: Fix Error Handle
    suspend fun signIn(
        activity: Activity,
        webClientId: String
    ): Result<FirebaseUser> {

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
                Result.failure(Exception("不支援的憑證類型: ${credential.type}"))
            }
        } catch (e: GetCredentialCancellationException) {
            // 使用者主動取消/滑掉 Bottom Sheet，不算系統錯誤
            Result.failure(Exception("使用者已取消登入"))
        } catch (e: GetCredentialException) {
            Result.failure(Exception("取得憑證失敗: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun signInAnonymously(): Result<FirebaseUser> = suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance()
            .signInAnonymously()
            .addOnCompleteListener{
                if (it.isSuccessful){
                    if (continuation.isActive){
                        continuation.resume(Result.success(it.result.user!!))
                    }
                }
            }

        continuation.invokeOnCancellation {
            //TODO: Fix Error Handle
            continuation.resume(Result.failure(Exception("Error")))
        }
    }



    /**
     * 將 Google Token 換取 Firebase Auth 登入狀態
     */
    private suspend fun signInWithFirebase(
        activity: Activity,
        idToken: String
    ): kotlin.Result<FirebaseUser> = suspendCancellableCoroutine { continuation ->
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        continuation.resume(kotlin.Result.success(user))
                    } else {
                        continuation.resume(kotlin.Result.failure(Exception("登入成功但找不到使用者資料")))
                    }
                } else {
                    continuation.resume(
                        kotlin.Result.failure(task.exception ?: Exception("Firebase 登入失敗"))
                    )
                }
            }
    }
}
