package com.lihan.core.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lihan.core.presentation.designsystem.ui.theme.BackgroundGradient

@Composable
fun AppBackgroundScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    ) {
    Scaffold(
        snackbarHost = snackbarHost,
        modifier = modifier.background(brush = BackgroundGradient),
        containerColor = Color.Transparent,
        topBar = topBar,
        bottomBar = bottomBar
    ) { paddingValues ->
        content(paddingValues)
    }
}