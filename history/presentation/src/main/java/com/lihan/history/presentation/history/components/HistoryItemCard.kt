package com.lihan.history.presentation.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.history.presentation.R
import com.lihan.history.presentation.history.util.formattedTeas

@Composable
fun HistoryItemCard(
    history: History,
    onOrderCancel: () -> Unit,
    modifier: Modifier = Modifier
) {

    val formattedTeas = history.teas.formattedTeas()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.history_order_code,history.orderCode),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = history.formattedCreateAt,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }

                StatusChip(
                    orderStatus = history.status
                )
                AnimatedVisibility(visible = history.status == OrderStatus.IN_ORDER) {
                    IconButton(
                        onClick = onOrderCancel
                    ) {
                        Icon(
                            imageVector = GongchaIcons.Close,
                            contentDescription = stringResource(R.string.remove_order)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    formattedTeas.forEach { text ->
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.order_total),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                    Text(
                        text = stringResource(R.string.dollar_sign,history.totalPrice),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }



    }

}


@Preview(showBackground = true)
@Composable
private fun CartItemCardPreview() {
    GongchaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HistoryItemCard(
                history = History(
                    id = "666555",
                    createAt = 0L,
                    status = OrderStatus.IN_ORDER,
                    totalPrice = 100,
                    teas = listOf(),
                    orderCode = "12314"
                ),
                onOrderCancel = {}
            )
        }
    }
}