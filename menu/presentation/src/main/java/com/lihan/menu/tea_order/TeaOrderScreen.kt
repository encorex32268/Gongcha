package com.lihan.menu.tea_order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TeaOrderRoot(
    viewModel: TeaOrderViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TeaOrderScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun TeaOrderScreen(
    state: TeaOrderState,
    onAction: (TeaOrderAction) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true)
@Composable
private fun TeaOrderScreenPreview() {
    TeaOrderScreen(
        state = TeaOrderState(),
        onAction = {}
    )
}