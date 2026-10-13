package com.lihan.menu.presentation.tea_order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.components.Counter


@Composable
fun OrderBottomBar(
    count: Int,
    totalPrice: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    canCheckout: Boolean = false
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Counter(
            count = count,
            onPlusClick = onPlusClick,
            onMinusClick = onMinusClick
        )

    }
}


@Preview
@Composable
private fun OrderCounterPreview() {
    GongchaTheme {
        OrderBottomBar(
            count = 10000,
            onPlusClick = {},
            onMinusClick = {},
            onCheckoutClick = {},
            totalPrice = 100
        )
    }
}