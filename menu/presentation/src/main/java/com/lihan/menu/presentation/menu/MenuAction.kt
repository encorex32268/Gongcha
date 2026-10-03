package com.lihan.menu.presentation.menu

import com.lihan.core.domain.Tea
import com.lihan.menu.presentation.menu.components.TeaType

sealed interface MenuAction {
    data object OnSelectStore: MenuAction
    data class OnTeaTypeSelect(val type: TeaType): MenuAction
    data class OnTeaClick(val tea: Tea): MenuAction
}