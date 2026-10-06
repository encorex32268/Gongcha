package com.lihan.menu.tea_order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.components.Counter
import com.lihan.menu.presentation.R


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