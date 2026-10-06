package com.lihan.auth.intro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun IntroRoot(
    viewModel: IntroViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    IntroScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun IntroScreen(
    state: IntroState,
    onAction: (IntroAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    GongchaTheme {
        IntroScreen(
            state = IntroState(),
            onAction = {}
        )
    }
}