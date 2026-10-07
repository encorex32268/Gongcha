package com.lihan.history.presentation.history

sealed interface HistoryAction {
    data object OnLoginClick: HistoryAction
}