package com.lihan.auth.presentation.login

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.auth.presentation.R
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginRoot(
    onNavigateToCodeVerify: () -> Unit,
    onNavigateToMenu: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            LoginEvent.OnNavigateToCodeVerify -> onNavigateToCodeVerify()
            LoginEvent.OnNavigateToMenu -> onNavigateToMenu()
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    val activity = LocalActivity.current
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.playstore_512),
                contentDescription = null
            )
            Spacer(Modifier.height(8.dp))
            IconButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                onClick = {
                    if (activity != null){
                        onAction(LoginAction.OnGoogleSignIn(activity))
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.google_sigin_button_pill),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

            Spacer(Modifier.height(4.dp))
            TextButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                onClick = {
                    onAction(LoginAction.OnWithoutSigningClick)
                }
            ) {
                Text(
                    text = stringResource(R.string.login_continue_without_signing),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        if (state.isVerifying){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }
        }

    }

}

@PreviewLightDark
@Composable
private fun Preview() {
    GongchaTheme {
        LoginScreen(
            state = LoginState(
                isVerifying = true
            ),
            onAction = {}
        )
    }
}