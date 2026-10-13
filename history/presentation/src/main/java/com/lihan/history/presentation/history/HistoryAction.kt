package com.lihan.history.presentation.history

sealed interface HistoryAction {
    data object OnSyncHistories: HistoryAction
    data class OnOrderCancelClick(val orderId: String): HistoryAction
    data object OnDismissCancelOrderDialog: HistoryAction
    data object OnConfirmCancelOrderClick: HistoryAction
}