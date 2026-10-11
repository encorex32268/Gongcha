package com.lihan.member.presentation.member.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.member.presentation.R
import com.lihan.core.presentation.ui.R as CoreR


@Composable
fun MemberNotLogin(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(CoreR.drawable.level_none),
            contentDescription = null,
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            text = stringResource(R.string.member_login),
            contentPadding = PaddingValues(12.dp),
            onClick = onLoginClick
        )
    }
}

@Preview
@Composable
private fun MemberNotLoginPreview() {
    GongchaTheme {
        MemberNotLogin(
            onLoginClick = {}
        )
    }
}