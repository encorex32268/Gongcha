package com.lihan.menu.presentation.menu

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.presentation.ui.UiText
import com.lihan.menu.presentation.menu.components.TeaType

data class MenuState(
    val userName: String?=null,
    val profileImageUrl: String?=null,
    val store: Store?=null,
    val teas: List<Tea> = emptyList(),
    val type: TeaType = TeaType.ALL,
    val error: UiText?=null
)