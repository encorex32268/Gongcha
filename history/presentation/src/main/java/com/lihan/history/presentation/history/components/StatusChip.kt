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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.history.presentation.R

@Composable
fun StatusChip(
    modifier: Modifier = Modifier,
    isProgress: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                color = if (isProgress){
                    MaterialTheme.colorScheme.primaryContainer
                }else{
                    MaterialTheme.colorScheme.secondaryContainer
                }
            )
            .padding(8.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Text(
            text = if (isProgress){
                stringResource(R.string.history_order_in_progress)
            }else{
                stringResource(R.string.history_order_completed)
            },
            color = if (isProgress){
                MaterialTheme.colorScheme.onPrimaryContainer
            }else{
                MaterialTheme.colorScheme.onSecondaryContainer
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun StatusChipPreview() {
    GongchaTheme {
        Column {
            StatusChip()
            StatusChip(isProgress = true)
        }
    }
}