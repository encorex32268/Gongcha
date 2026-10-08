package com.lihan.gongcha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CartRepository,
    private val appDataStore: AppDataStore
): ViewModel() {

    private var hasLoadedInitialData = false


    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.onStart {
        if (!hasLoadedInitialData) {
            observePhoneNumber()
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ""
    )
    init {
        //TODO: observe phoneNumber, if empty navigate to login screen
    }

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount
        .onStart {
            if (!hasLoadedInitialData) {
                observeCartItemCount()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0
        )

    private fun observeCartItemCount(){
        repository
            .getCarItemsCount()
            .distinctUntilChanged()
            .onEach { count ->
                _cartItemCount.update { count }

            }.launchIn(viewModelScope)
    }

    private fun observePhoneNumber(){
        appDataStore
            .phoneNumber
            .onEach {

            }.launchIn(viewModelScope)
    }

}