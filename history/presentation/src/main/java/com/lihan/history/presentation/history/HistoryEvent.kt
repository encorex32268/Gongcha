package com.lihan.history.presentation.history

import com.lihan.core.presentation.ui.UiText

sealed interface HistoryEvent {
    data object OnCancelOrderSuccess: HistoryEvent
    data class OnCancelOrderFailed(val uiText: UiText): HistoryEvent
}