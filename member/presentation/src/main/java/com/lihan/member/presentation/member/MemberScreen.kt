@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.member.presentation.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.domain.model.User
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.member.presentation.R
import com.lihan.member.presentation.member.components.MemberCard
import com.lihan.member.presentation.member.components.MemberNotLogin
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemberRoot(
    viewModel: MemberViewModel = koinViewModel()
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
        if (state.user == null){
            MemberNotLogin(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(top = 64.dp),
                onLoginClick = {
                    onAction(MemberAction.OnLoginClick)
                }
            )
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                MemberCard(
                    profileImageUrl = state.user.imageUrl,
                    userName = state.user.name.ifBlank { "User-${state.user.id.take(6)}" },
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
                user = User(
                    id = "1234",
                    level = MemberLevel.SILVER,
                    name = "Li",
                    points = 1000,
                    memberCode = "KC123456"
                )
            ),
            onAction = {}
        )
    }
}