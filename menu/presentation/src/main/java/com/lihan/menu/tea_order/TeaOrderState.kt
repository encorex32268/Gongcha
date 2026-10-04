package com.lihan.menu.tea_order

import androidx.compose.runtime.Immutable
import com.lihan.core.domain.model.Tea

@Immutable
data class TeaOrderState(
    val tea: Tea? = null,
    val isLoading: Boolean = false
)