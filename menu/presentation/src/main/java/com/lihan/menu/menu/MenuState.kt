package com.lihan.menu.menu

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.presentation.ui.UiText
import com.lihan.menu.menu.components.TeaType


data class MenuState(
    val userName: String?=null,
    val store: Store?=null,
    val teas: List<Tea> = emptyList(),
    val type: TeaType = TeaType.ALL,
    val error: UiText?=null
)