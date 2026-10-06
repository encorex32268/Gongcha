package com.lihan.menu.tea_order.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.model.Topping
import com.lihan.menu.presentation.R

@Composable
fun ToppingSelection(
    selectingToppings: List<Topping>,
    onToppingClick: (Topping) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.topping_original)
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            maxItemsInEachRow = 2,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Topping.originalToppings.forEach { topping ->
                ToppingItem(
                    modifier = Modifier.weight(1f),
                    isSelected = topping in selectingToppings,
                    topping = topping,
                    onToppingClick = {
                        onToppingClick(topping)
                    }
                )
            }
        }

        Text(text = stringResource(R.string.topping_flavor))
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Topping.flavorToppings.forEach { topping ->
                FlavorItem(
                    modifier = Modifier.weight(1f),
                    isSelected = topping in selectingToppings,
                    topping = topping,
                    onToppingClick = {
                        onToppingClick(topping)
                    }
                )
            }
        }



    }
}

@Composable
private fun FlavorItem(
    isSelected: Boolean,
    topping: Topping,
    onToppingClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected){
                    MaterialTheme.colorScheme.primary
                }else MaterialTheme.colorScheme.outlineVariant
            )
            .clickable(onClick = onToppingClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = stringResource(topping.nameZhResId),
            color = if (isSelected){
                MaterialTheme.colorScheme.primary
            }else LocalContentColor.current
        )
        Text(
            text = stringResource(topping.nameEnResId),
            color = MaterialTheme.colorScheme.outline,
            fontSize = 12.sp
        )
        Text(
            text = "+${topping.price}",
            color = if (isSelected){
                MaterialTheme.colorScheme.primary
            }else LocalContentColor.current
        )
    }
}

@Composable
private fun ToppingItem(
    isSelected: Boolean,
    topping: Topping,
    onToppingClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected){
                    MaterialTheme.colorScheme.primary
                }else MaterialTheme.colorScheme.outlineVariant
            )
            .clickable(onClick = onToppingClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(topping.nameZhResId),
            color = if (isSelected){
                MaterialTheme.colorScheme.primary
            }else LocalContentColor.current
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(topping.nameEnResId),
            color = MaterialTheme.colorScheme.outline,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun ToppingSelectionPreview() {
    GongchaTheme {
        ToppingSelection(
            selectingToppings = listOf(
                Topping.PEARL
            ),
            onToppingClick = {}
        )
    }
}