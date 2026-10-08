@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.member.presentation.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons.ArrowBack
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.member.presentation.R
import com.lihan.member.presentation.member.components.MemberCard

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.user == null){
                MemberCard(
                    userName = stringResource(R.string.guest),
                    level = MemberLevel.NONE,
                    pointsInt = 0
                )
            }else{
                MemberCard(
                    userName = state.user.name,
                    level = state.user.level,
                    pointsInt = state.user.points,
                )
            }

        }


    }

}

@Preview(showBackground = true)
@Composable
private fun MemberScreenPreview() {
    GongchaTheme {
        MemberScreen(
            state = MemberState(
                user = null
            ),
            onAction = {}
        )
    }
}