package com.lihan.menu.menu

import com.lihan.core.domain.model.Tea
import com.lihan.menu.menu.components.TeaType

sealed interface MenuAction {
    data object OnSelectStore: MenuAction
    data object OnNavigateToMember: MenuAction
    data class OnTeaTypeSelect(val type: TeaType): MenuAction
    data class OnTeaClick(val tea: Tea): MenuAction
}