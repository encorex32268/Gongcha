@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.auth.AuthRepository
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.util.onError
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.presentation.ui.toUiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


class CartViewModel(
    private val repository: CartRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private var hasLoadedInitialData = false

    private var updateItemCountJob: Job?=null

    private val _uiEvent = Channel<CartEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


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
            is CartAction.OnMinusClick -> onMinusItem(action.id)
            is CartAction.OnPlusClick -> onPlusItem(action.id)
            is CartAction.OnShowDeleteDialog -> showDeleteDialog(action.text,action.cartItemId)
            CartAction.OnDismissDeleteDialog -> dismissDeleteDialog()
            CartAction.OnConfirmDeleteClick -> onDeleteCartItem()
            CartAction.OnCheckout -> checkout()
        }
    }

    private fun checkout(){
        if (_state.value.isCheckoutLoading){
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isCheckoutLoading = true) }
            try {
                repository
                    .checkout()
                    .onError { error ->
                        _state.update {
                            it.copy(isCheckoutLoading = false)
                        }
                        _uiEvent.send(CartEvent.OnCheckoutFail(errorMessage = error.toUiText()))
                    }
                    .onSuccess {
                        val currentUser = authRepository.user.first()
                        if (currentUser != null){
                            repository.clearCartItemsByUserId(userId = currentUser.id)
                        }

                        _state.update { it.copy(
                            isCheckoutLoading = false)
                        }
                        _uiEvent.send(CartEvent.OnCheckoutSuccess)
                    }
            } catch (e: Exception) {
                ensureActive()
                e.printStackTrace()
                _state.update { it.copy(isCheckoutLoading = false) }
            }
        }
    }




    private fun showDeleteDialog(text: String, cartItemId: Int?){
        if (cartItemId == null){
            return
        }
        _state.update { it.copy(
            deleteItemContent = text,
            selectedCartItemId = cartItemId
        ) }
    }

    private fun dismissDeleteDialog() {
        _state.update { it.copy(
            deleteItemContent = null,
            selectedCartItemId = null
        ) }
    }

    private fun onDeleteCartItem(){
        if (state.value.selectedCartItemId == null){
            return
        }
        viewModelScope.launch {
            repository.removeCartItem(state.value.selectedCartItemId!!)
            _state.update { it.copy(
                deleteItemContent = null,
                selectedCartItemId = null
            ) }
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
        authRepository.user
            .flatMapLatest { user ->
                if (user == null){
                    emptyFlow()
                }else{
                    repository.getCartItemsByUserId(user.id)
                }
            }.onEach { cartItems ->
                val storeItems = cartItems.sortedByDescending { it.id }
                _state.update { it.copy(
                    cartItems = storeItems
                ) }
            }.launchIn(viewModelScope)
    }

}