package com.lihan.cart.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.cart.presentation.R
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun CartEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.cart_emtpy_image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.cart_empty)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CartEmptyViewPreview() {
    GongchaTheme {
        CartEmptyView()
    }
}