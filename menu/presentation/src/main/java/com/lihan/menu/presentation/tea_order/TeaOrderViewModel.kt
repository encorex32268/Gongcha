package com.lihan.menu.presentation.tea_order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Ice
import com.lihan.core.domain.model.Size
import com.lihan.core.domain.model.Sugar
import com.lihan.core.domain.model.Topping
import com.lihan.menu.domain.MenuRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeaOrderViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CartRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {

    private val teaId: String? = savedStateHandle.get<String>("teaId")
    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<TeaOrderEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(TeaOrderState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeTea()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TeaOrderState()
        )

    fun onAction(action: TeaOrderAction) {
        when (action) {
            TeaOrderAction.OnNavigateBack -> Unit
            TeaOrderAction.OnAddToCartClick -> addToCart()
            TeaOrderAction.OnMinusClick -> minusCount()
            TeaOrderAction.OnPlusClick -> plusCount()
            is TeaOrderAction.OnToppingClick -> toppingClick(action.topping)
            is TeaOrderAction.OnIceSelect -> onIceSelect(action.ice)
            is TeaOrderAction.OnSizeSelect -> onSizeSelect(action.size)
            is TeaOrderAction.OnSugarSelect -> onSugarSelect(action.sugar)
        }
    }

    private fun onIceSelect(ice: Ice){
        _state.update { it.copy(
            ice = ice
        ) }
    }
    private fun onSizeSelect(size: Size){
        _state.update { it.copy(
            size = size
        ) }
    }
    private fun onSugarSelect(sugar: Sugar){
        _state.update { it.copy(
            sugar = sugar
        ) }
    }

    private fun addToCart() {
        viewModelScope.launch {
            val currentState = state.value
            val tea = currentState.tea ?: return@launch

            repository
                .addCartItem(
                    CartItem(
                        teaId = tea.id,
                        teaName = tea.nameZh,
                        storeName = "",
                        size = currentState.size.name,
                        toppings = currentState.toppings.map { it.name },
                        sugar = currentState.sugar!!.name,
                        ice = currentState.ice!!.name,
                        count = currentState.count,
                        price = currentState.price + currentState.toppings.sumOf { it.price },
                        imageUrl = tea.imageUrl
                    )
                )

            _uiEvent.send(TeaOrderEvent.OnSavedCartItem)
        }
    }

    private fun toppingClick(topping: Topping){
        val currentToppings = state.value.toppings
        val newToppings = if (topping in currentToppings){
            currentToppings - topping
        }else{
            currentToppings + topping
        }

        _state.update { it.copy(
            toppings = newToppings
        ) }
    }

    private fun minusCount() {
        _state.update { it.copy(
            count = it.count - 1
        ) }
    }

    private fun plusCount() {
        _state.update { it.copy(
            count = it.count + 1
        ) }
    }

    private fun observeTea() {
        viewModelScope.launch {
            if (teaId == null){
                return@launch
            }

            val tea = menuRepository.getTeaById(teaId)
            if (tea != null) {
                _state.update { it.copy(tea = tea) }
            }
        }

    }
}