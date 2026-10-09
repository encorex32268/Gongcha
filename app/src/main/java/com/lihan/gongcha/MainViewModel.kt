package com.lihan.gongcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.CartRepository
import com.lihan.gongcha.navigation.MenuGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CartRepository,
    private val appDataStore: AppDataStore
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        observeCartItemCount()
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                _state.update { it.copy(
                    startDestination = MenuGraph
                ) }
            }
            _state.update { it.copy(isReady = true) }
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