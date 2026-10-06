package com.lihan.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.R

@Composable
fun Counter(
    count: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconButtonSize: Dp = 28.dp,
    textSize: TextUnit = 24.sp,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedIconButton(
            modifier = Modifier.size(iconButtonSize),
            onClick = onMinusClick,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            ),
            enabled = count > 1
        ) {
            Icon(
                imageVector = GongchaIcons.Minus,
                contentDescription = stringResource(R.string.minus),
                tint = MaterialTheme.colorScheme.outline
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .padding(bottom = 2.dp),
            contentAlignment = Alignment.TopCenter
        ){
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = textSize
                )
            )
        }

        OutlinedIconButton(
            modifier = Modifier.size(iconButtonSize),
            onClick = onPlusClick,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
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
private fun CounterPreview() {
    GongchaTheme {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ){
            Counter(
                count = 1,
                onPlusClick = {},
                onMinusClick = {}
            )

            PrimaryButton(
                text = "Button",
                onClick = {}
            )
        }
    }
}