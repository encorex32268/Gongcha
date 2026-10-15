package com.lihan.auth.presentation.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lihan.auth.presentation.R
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.designsystem.ui.theme.GoogleSignIn
import com.lihan.core.presentation.designsystem.ui.theme.GoogleSignInText


@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = GoogleSignIn,
            contentColor = GoogleSignInText
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.logo_google),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.google_sign_in),
            style = MaterialTheme.typography.bodyMedium
        )
    }

}


@PreviewLightDark
@Composable
private fun GoogleSignInButtonPreview() {
    GongchaTheme {
        GoogleSignInButton(
            onClick = {}
        )
    }
}