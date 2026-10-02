package com.lihan.menu.presentation.menu

import com.lihan.core.domain.Store
import com.lihan.core.domain.Tea


data class MenuState(
    val userName: String?=null,
    val store: Store?=null,
    val teas: List<Tea> = emptyList(),
)