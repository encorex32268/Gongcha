package com.lihan.menu.presentation.menu.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.GongchaChips
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun TeaTypeChips(
    selectedTeaType: TeaType,
    onTypeSelect: (TeaType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(
            state = rememberScrollState()
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TeaType.entries.forEach { teaType ->
            GongchaChips(
                text = teaType.toStringResource(),
                onClick = { onTypeSelect(teaType) },
                selected = selectedTeaType == teaType
            )
        }
    }

}

@Preview
@Composable
private fun TeaTypeChipsPreview() {
    GongchaTheme {
        TeaTypeChips(
            selectedTeaType = TeaType.ORIGINAL,
            onTypeSelect = {}
        )
    }
}