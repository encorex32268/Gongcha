package com.lihan.menu.presentation.store

import com.lihan.core.domain.model.Store

data class StoreState(
    val stores: Map<String, List<Store>> = hashMapOf()
)