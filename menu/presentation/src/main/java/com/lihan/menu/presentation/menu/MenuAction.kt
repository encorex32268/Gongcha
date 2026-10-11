package com.lihan.menu.presentation.menu

import com.lihan.core.domain.model.Tea
import com.lihan.menu.presentation.menu.components.TeaType

sealed interface MenuAction {
    data object OnSelectStore: MenuAction
    data object OnNavigateToMember: MenuAction
    data class OnTeaTypeSelect(val type: com.lihan.menu.presentation.menu.components.TeaType): MenuAction
    data class OnTeaClick(val tea: Tea): MenuAction
}