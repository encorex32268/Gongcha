package com.lihan.menu.presentation.menu

sealed interface MenuAction {
    data object OnSelectStore: MenuAction
}