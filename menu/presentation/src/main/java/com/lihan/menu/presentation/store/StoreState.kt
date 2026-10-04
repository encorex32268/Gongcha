package com.lihan.menu.presentation.store

import com.lihan.core.domain.model.Store

data class StoreState(
    val isLoading: Boolean = false,
    val stores: List<Store> = emptyList()
)