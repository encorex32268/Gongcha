package com.lihan.core.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.model.User
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.network.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val appDataStore: AppDataStore,
    private val remoteDataSource: RemoteDataSource,
    private val applicationScope: CoroutineScope
): AuthRepository {

    private val firebaseAuth =  FirebaseAuth.getInstance()

    override val user: Flow<User?>
        get() = appDataStore.user

    override suspend fun setUser(user: User) {
        appDataStore.setUser(user)
    }

    override suspend fun createUser(user: User): EmptyResult<DataError> {
       return remoteDataSource
           .createUser(user)
           .onSuccess {
               applicationScope.launch {
                   appDataStore.setUser(user)
               }.join()
           }
    }

    override suspend fun getUserById(id: String): Result<User?, DataError> {
        return remoteDataSource.getUserById(id)
            .onSuccess { user ->
                if (user != null){
                    applicationScope.launch {
                        appDataStore.setUser(user)
                    }.join()
                }
            }

    }

    override suspend fun signOut() {
        appDataStore.clean()
        firebaseAuth.signOut()
    }

}