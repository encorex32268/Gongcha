package com.lihan.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lihan.core.domain.model.CartItem
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.components.Counter
import com.lihan.core.domain.model.Ice
import com.lihan.core.domain.model.Size
import com.lihan.core.domain.model.Sugar
import com.lihan.core.domain.model.Topping
import com.lihan.core.presentation.ui.tea_order.nameZhResId
import com.lihan.core.presentation.ui.tea_order.toStringResource

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = cartItem.imageUrl,
                modifier = Modifier
                    .size(120.dp),
                contentDescription = cartItem.teaName,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = cartItem.teaName,
                        fontWeight = FontWeight.SemiBold
                    )
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = onDeleteClick
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = GongchaIcons.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
                Text(
                    text = "${Size.valueOf(cartItem.size).toStringResource()}・${Ice.valueOf(cartItem.ice).toStringResource()}・${Sugar.valueOf(cartItem.sugar).toStringResource()}",
                    fontWeight = FontWeight.Medium
                )
                if (cartItem.toppings.isNotEmpty()){
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        cartItem.toppings.forEachIndexed { index, toppingName ->
                            val topping = Topping.valueOf(toppingName)
                            val text = "${stringResource(topping.nameZhResId)}(+${topping.price})"
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(fraction = 0.1f)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Counter(
                        modifier = Modifier.weight(1f),
                        count = cartItem.count,
                        onMinusClick = onMinusClick,
                        onPlusClick = onPlusClick
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "NT$ ${(cartItem.count * cartItem.price)}",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${cartItem.count} x NT$ ${cartItem.price}",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun CartItemCardPreview() {
    GongchaTheme {
        CartItemCard(
            cartItem = CartItem(
                teaId = "",
                teaName = "茉香綠",
                imageUrl = "",
                storeName = "高雄市苓雅區光華一路230號",
                size = Size.M.name,
                toppings = Topping.entries.shuffled().take(6).map { it.name },
                sugar = Sugar.HALF.name,
                ice = Ice.LITTLE.name,
                count = 10,
                price = 100
            ),
            onPlusClick = {},
            onMinusClick = {},
            onDeleteClick = {}
        )
    }
}