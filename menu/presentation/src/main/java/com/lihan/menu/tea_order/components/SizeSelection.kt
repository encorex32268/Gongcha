package com.lihan.menu.tea_order.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.tea_order.Size
import com.lihan.core.presentation.ui.tea_order.Size.Companion.toMLStringResource
import com.lihan.core.presentation.ui.tea_order.Size.Companion.toStringResource
import com.lihan.menu.presentation.R

@Composable
fun SizeSelection(
    currentSize: Size,
    priceM: String,
    priceL: String,
    onSizeClick: (Size) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SizeCard(
            priceText = priceM,
            size = Size.M,
            modifier = Modifier.weight(1f),
            isSelected = currentSize == Size.M,
            onSizeClick = onSizeClick
        )
        SizeCard(
            priceText = priceL,
            size = Size.L,
            modifier = Modifier.weight(1f),
            isSelected = currentSize == Size.L,
            onSizeClick = onSizeClick
        )
    }
}

@Composable
private fun SizeCard(
    priceText: String,
    size: Size,
    onSizeClick: (Size) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
){
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onSizeClick(size)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected){
                MaterialTheme.colorScheme.primary
            }else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = size.toStringResource(),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else LocalContentColor.current
                )
            )
            Text(
                text = stringResource(
                    R.string.size_price,
                    size.toMLStringResource(),
                    priceText
                )
            )
        }

    }

}



@Preview
@Composable
private fun SizeSelectionPreview() {
    GongchaTheme {
        SizeSelection(
            currentSize = Size.L,
            priceM = "70",
            priceL = "100",
            onSizeClick = {

            }
        )
    }
}