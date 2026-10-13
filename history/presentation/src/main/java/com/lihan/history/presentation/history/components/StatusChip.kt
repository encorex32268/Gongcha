package com.lihan.history.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.history.toStringResourceId

@Composable
fun StatusChip(
    orderStatus: OrderStatus,
    modifier: Modifier = Modifier
) {
    val color = when(orderStatus){
        OrderStatus.IN_ORDER -> MaterialTheme.colorScheme.tertiaryContainer
        OrderStatus.IN_PROGRESS ->  MaterialTheme.colorScheme.primaryContainer
        OrderStatus.COMPLETED -> MaterialTheme.colorScheme.secondaryContainer
    }
    val textColor = when(orderStatus){
        OrderStatus.IN_ORDER -> MaterialTheme.colorScheme.onTertiaryContainer
        OrderStatus.IN_PROGRESS ->  MaterialTheme.colorScheme.onPrimaryContainer
        OrderStatus.COMPLETED -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = orderStatus.toStringResourceId(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = textColor
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun StatusChipPreview() {
    GongchaTheme {
        Column {
            StatusChip(orderStatus = OrderStatus.IN_ORDER)
            StatusChip(orderStatus = OrderStatus.COMPLETED)
            StatusChip(orderStatus = OrderStatus.IN_PROGRESS)
        }
    }
}