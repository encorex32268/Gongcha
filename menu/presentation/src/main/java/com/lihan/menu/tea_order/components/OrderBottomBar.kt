package com.lihan.menu.tea_order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.menu.presentation.R


@Composable
fun OrderBottomBar(
    count: Int,
    price: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val totalPrice = remember(count) {
        count * price
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OrderCounter(
            count = count,
            onPlusClick = onPlusClick,
            onMinusClick = onMinusClick
        )
        PrimaryButton(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            text = stringResource(R.string.checkout,totalPrice.toString()),
            onClick = onCheckoutClick
        )
    }
}



@Composable
private fun OrderCounter(
    count: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        IconButton(
            modifier = Modifier.size(28.dp),
            onClick = onMinusClick
        ) {
            Icon(
                imageVector = GongchaIcons.Minus,
                contentDescription = stringResource(R.string.minus),
                tint = MaterialTheme.colorScheme.outline
            )
        }

        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = " $count ",
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 24.sp,
            )
        )

        IconButton(
            modifier = Modifier.size(28.dp),
            onClick = onPlusClick
        ) {
            Icon(
                imageVector = GongchaIcons.Plus,
                contentDescription = stringResource(R.string.plus),
                tint = MaterialTheme.colorScheme.outline
            )
        }
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
            price = 1
        )
    }
}