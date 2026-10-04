package com.lihan.menu.menu

sealed interface MenuEvent {
    data object OnSendStorePickWarning: MenuEvent
    data class OnNavigateToTeaOrder(val teaId: String): MenuEvent
}