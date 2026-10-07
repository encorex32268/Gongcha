package com.lihan.menu.tea_order.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.tea_order.Sugar
import com.lihan.core.presentation.ui.tea_order.Sugar.Companion.toPercent
import com.lihan.core.presentation.ui.tea_order.Sugar.Companion.toStringResource

@Composable
fun SugarSelection(
    currentSugar: Sugar?,
    onSugarClick: (Sugar) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
        space = (-4).dp
    ) {
        Sugar.entries.forEach { sugar ->
            SegmentedButton(
                selected = currentSugar == sugar,
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    onSugarClick(sugar)
                },
                label = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = sugar.toStringResource())
                        Text(text = sugar.toPercent(), textAlign = TextAlign.Center)
                    }

                },
                border = BorderStroke(width = 0.dp, color = Color.Transparent),
                icon = {},
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Preview
@Composable
private fun SugarSelectionPreview() {
    GongchaTheme {
        SugarSelection(
            currentSugar = Sugar.LESS,
            onSugarClick = {}
        )
    }
}