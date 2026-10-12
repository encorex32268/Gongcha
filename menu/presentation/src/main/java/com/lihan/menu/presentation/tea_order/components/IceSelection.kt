package com.lihan.menu.presentation.tea_order.components

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
import com.lihan.core.domain.model.Ice
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.tea_order.toStringResource
import com.lihan.core.presentation.ui.tea_order.toSupportText

@Composable
fun IceSelection(
    currentIce: Ice?,
    onIceClick: (Ice) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
        space = (-4).dp
    ) {
        Ice.entries.forEach { ice ->
            SegmentedButton(
                selected = currentIce == ice,
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    onIceClick(ice)
                },
                label = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = ice.toStringResource())
                        Text(text = ice.toSupportText(), textAlign = TextAlign.Center)
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
private fun IceSelectionPreview() {
    GongchaTheme {
        IceSelection(
            onIceClick = {},
            currentIce = Ice.LITTLE
        )
    }
}