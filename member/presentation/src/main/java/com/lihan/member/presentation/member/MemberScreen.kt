@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.member.presentation.member

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons.ArrowBack
import com.lihan.member.presentation.R

@Composable
fun MemberRoot(
    viewModel: MemberViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MemberScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MemberScreen(
    state: MemberState,
    onAction: (MemberAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    stringResource(R.string.member_title)
                ) },
            )
        }
    ) { padding ->



    }

}

@Preview(showBackground = true)
@Composable
private fun MemberScreenPreview() {
    MemberScreen(
        state = MemberState(),
        onAction = {}
    )
}