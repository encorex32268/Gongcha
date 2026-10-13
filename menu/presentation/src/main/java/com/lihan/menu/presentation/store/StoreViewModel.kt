package com.lihan.menu.presentation.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.menu.domain.MenuRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoreViewModel(
    private val repository: MenuRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiEvent = Channel<StoreEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(StoreState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeStores()
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = StoreState()
        )

    init {
        viewModelScope.launch {
            repository.syncStoresData()
        }
    }

    fun onAction(action: StoreAction) {
        when (action) {
            StoreAction.OnNavigateBack -> Unit
            is StoreAction.OnStoreSelected -> {
                viewModelScope.launch {
                    repository.setLatestSelectedStore(action.id)
                    _uiEvent.send(StoreEvent.OnSavedSelectedStore)
                }
            }
        }
    }

    private fun observeStores(){
        repository
            .stores
            .onEach {  stores ->
                val mapStores = stores
                    .sortedBy { it.name }
                    .groupBy { it.region }
                    .toSortedMap(compareBy { region ->
                        when (region) {
                            "北區" -> 1
                            "中區" -> 2
                            "南區" -> 3
                            else -> 4
                        }
                    })
                _state.update { it.copy(
                    stores = mapStores
                ) }
            }
            .launchIn(viewModelScope)
    }
}