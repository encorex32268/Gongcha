package com.lihan.menu.tea_order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun SizeSelection(
    currentSize: Size,
    priceM: String,
    priceL: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Size.entries.forEach { size ->
            TeaCup(
                isSelected = currentSize == size,
                size = size
            )
        }
    }
}

@Composable
private fun TeaCup(
    isSelected: Boolean,
    size: Size,
    modifier: Modifier = Modifier
){
    Column(
        modifier =modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Icon(
                modifier = Modifier
                    .size(
                        when(size){
                            Size.M -> 12.dp
                            Size.L -> 24.dp
                        }
                    ),
                imageVector = GongchaIcons.DrinkMenu,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = size.name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }


}

@Preview
@Composable
private fun SizeSelectionPreview() {
    GongchaTheme {
        SizeSelection(
            currentSize = Size.L,
            priceM = "60",
            priceL = "120"
        )
    }
}