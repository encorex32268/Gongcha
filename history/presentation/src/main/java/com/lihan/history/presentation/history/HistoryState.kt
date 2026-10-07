package com.lihan.history.presentation.history

import com.lihan.core.domain.model.History

data class HistoryState(
    val isLogin: Boolean = false,
    val items: List<History> = emptyList()
)