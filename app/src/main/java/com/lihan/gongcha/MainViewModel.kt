@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.gongcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.util.Result
import com.lihan.gongcha.navigation.AuthGraph
import com.lihan.gongcha.navigation.MenuGraph
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CartRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private var hasInitLoadedData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasInitLoadedData){
                observeCartItemCount()
                hasInitLoadedData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            MainState()
        )

    init {
        initial()
    }

    private fun initial() {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid?:""
            println("Uid: ${uid}")
            val startDestination = if (uid.isBlank()){
                AuthGraph
            }else{
                when(
                    val result = authRepository.getUserById(uid)
                ){
                    is Result.Error-> AuthGraph
                    is Result.Success-> {
                        if (result.data == null){
                            AuthGraph
                        }else{
                            MenuGraph
                        }
                    }
                }
            }
            _state.update { it.copy(
                startDestination = startDestination,
                isReady = true
            ) }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    private fun observeCartItemCount() {

        authRepository.user
            .flatMapLatest { user ->
                if (user == null){
                    emptyFlow()
                }else{
                    repository.getCarItemsCountByUserId(user.id)
                }
            }.onEach { count ->
                _state.update { it.copy(
                    cartItemCount = count
                ) }
            }.launchIn(viewModelScope)
    }
}