package com.lihan.menu.presentation.menu

import com.lihan.core.domain.model.Tea
import com.lihan.menu.presentation.menu.components.TeaType

sealed interface MenuAction {
    data object OnSelectStore: MenuAction
    data object OnNavigateToMember: MenuAction
    data class OnTeaTypeSelect(val type: TeaType): MenuAction
    data class OnTeaClick(val tea: Tea): MenuAction
    data object OnShowSignOutDialog: MenuAction
    data object OnDismissSignOutDialog: MenuAction
    data object OnSignOutDialogConfirmClick: MenuAction
    data object OnShowSelectStoreWarningDialog: MenuAction
    data object OnDismissSelectStoreWarningDialog: MenuAction
}