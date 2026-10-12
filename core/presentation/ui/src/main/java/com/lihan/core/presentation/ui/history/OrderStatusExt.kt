package com.lihan.core.presentation.ui.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.presentation.ui.R

@Composable
fun OrderStatus.toStringResourceId(): String {
    return when(this){
        OrderStatus.IN_ORDER -> stringResource(R.string.order_status_in_order)
        OrderStatus.IN_PROGRESS -> stringResource(R.string.order_status_in_progress)
        OrderStatus.COMPLETED -> stringResource(R.string.order_status_completed)
    }
}