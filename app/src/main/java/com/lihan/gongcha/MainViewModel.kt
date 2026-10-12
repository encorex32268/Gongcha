package com.lihan.gongcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.util.Result
import com.lihan.gongcha.navigation.AuthGraph
import com.lihan.gongcha.navigation.MenuGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid?:""
            val startDestination = when(
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
            _state.update { it.copy(
                startDestination = startDestination,
                isReady = true
            ) }
        }
    }

    private fun observeCartItemCount() {
        repository
            .getCarItemsCount()
            .distinctUntilChanged()
            .onEach { count ->
                _state.update { it.copy(
                    cartItemCount = count
                ) }
            }.launchIn(viewModelScope)
    }
}