@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.member.presentation.member

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.domain.model.User
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
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
                actions = {
                    IconButton(
                        onClick = {
                            onAction(MemberAction.OnShowQRCode)
                        }
                    ) {
                        Icon(
                            imageVector = GongchaIcons.QRCode,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
    ) { padding ->
        when{
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
            else -> {
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


        if (state.isShowQRCode){
            Dialog(
                onDismissRequest = { onAction(MemberAction.OnDismissQRCode) },
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    state.qrCode?.let { bitmap ->
                        Image(
                            modifier = Modifier.clip(RoundedCornerShape(32.dp)),
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = stringResource(R.string.member_qr_code),
                            contentScale = ContentScale.Fit,
                        )
                    } ?: CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
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