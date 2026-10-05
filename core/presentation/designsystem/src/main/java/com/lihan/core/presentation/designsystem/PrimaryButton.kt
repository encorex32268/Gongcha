package com.lihan.core.presentation.designsystem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    trailingIcon: @Composable (() -> Unit)?=null,
    leadingIcon: @Composable (() -> Unit)?=null
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(Modifier.width(4.dp))
        }
        Text(text = text)
        if (trailingIcon != null) {
            Spacer(Modifier.width(4.dp))
            trailingIcon()
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    GongchaTheme {
        PrimaryButton(
            text = "Button",
            onClick = {},
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.location),
                    contentDescription = null
                )
            },
        )
    }

}