package com.lihan.cart.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.GongchaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class CartViewModel(
    private val repository: GongchaRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private var updateItemCountJob: Job?=null
    private var preDeleteCartItemId: Int?=null

    private val _state = MutableStateFlow(CartState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeCartItems()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CartState()
        )

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnDeleteClick -> TODO()
            is CartAction.OnMinusClick -> onMinusItem(action.id)
            is CartAction.OnPlusClick -> onPlusItem(action.id)
            is CartAction.OnShowDeleteDialog -> {
                preDeleteCartItemId = action.cartItemId
                _state.update { it.copy(
                    deleteItemContent = action.text
                ) }
            }

            CartAction.OnDismissDeleteDialog -> {
                preDeleteCartItemId = null
                _state.update { it.copy(
                    deleteItemContent = null
                ) }
            }

            CartAction.OnConfirmDeleteClick -> onDeleteCartItem()
        }
    }

    private fun onDeleteCartItem(){
        if (preDeleteCartItemId == null){
            return
        }
        viewModelScope.launch {
            repository.removeCartItem(preDeleteCartItemId!!)
            _state.update { it.copy(
                deleteItemContent = null
            ) }
            preDeleteCartItemId = null
        }
    }

    private fun onMinusItem(id: Int?){
        if(id == null){
            return
        }
        updateItemCountJob?.cancel()
        updateItemCountJob = viewModelScope.launch {
            delay(300L.milliseconds)
            val currentState = state.value
            val currentCount = currentState.cartItems.find { it.id == id }?.count ?: return@launch
            if (currentCount == 1) return@launch
            repository.updateCartItemCount(id, currentCount - 1)
        }
    }


    private fun onPlusItem(id: Int?){
        if(id == null){
            return
        }
        updateItemCountJob?.cancel()
        updateItemCountJob = viewModelScope.launch {
            delay(300L.milliseconds)
            val currentState = state.value
            val currentCount = currentState.cartItems.find { it.id == id }?.count ?: return@launch
            repository.updateCartItemCount(id, currentCount + 1)
        }
    }


    private fun observeCartItems(){
        repository
            .getCarItems()
            .onEach { cartItems ->
                val storeItems = cartItems.sortedByDescending { it.id }
                _state.update { it.copy(
                    cartItems = storeItems
                ) }

            }.launchIn(viewModelScope)
    }

}