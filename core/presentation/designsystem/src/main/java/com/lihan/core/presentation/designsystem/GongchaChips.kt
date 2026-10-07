package com.lihan.core.presentation.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun GongchaChips(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?=null,
    selected: Boolean = false,
    enabled: Boolean = true
) {
    FilterChip(
        selected = selected,
        onClick = {
            onClick?.invoke()
        },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled,
        label = {
            Text(
                text = text
            )
        }
    )
}

@Preview
@Composable
private fun GongchaChipsPreview() {
    GongchaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            GongchaChips(
                text = "Tea",
                onClick = {}
            )
            GongchaChips(
                text = "Tea2",
                selected = true,
                onClick = {}
            )

        }
    }
}