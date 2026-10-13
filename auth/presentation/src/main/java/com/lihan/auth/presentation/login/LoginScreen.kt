package com.lihan.auth.presentation.login

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
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
    val focusManager = LocalFocusManager.current
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

//            OutlinedTextField(
//                state = state.textFieldState,
//                shape = CircleShape,
//                lineLimits = TextFieldLineLimits.SingleLine,
//                inputTransformation = InputTransformation.maxLength(13),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Phone,
//                    imeAction = ImeAction.Done
//                ),
//                onKeyboardAction = {
//                    focusManager.clearFocus()
//                },
//                placeholder = {
//                    Text(
//                        text = "0912345678",
//                        style = MaterialTheme.typography.titleMedium.copy(
//                            color = MaterialTheme.colorScheme.surfaceDim
//                        )
//                    )
//                },
//                supportingText = {
//                    if (state.errorMessage!=null){
//                        Text(
//                            text = state.errorMessage.asString(),
//                            style = MaterialTheme.typography.bodySmall.copy(
//                                color = MaterialTheme.colorScheme.error
//                            ),
//
//                        )
//                    }
//                },
//                textStyle = MaterialTheme.typography.titleMedium.copy(
//                    textAlign = TextAlign.Center
//                )
//            )
//            Spacer(Modifier.height(8.dp))
//            PrimaryButton(
//                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
//                contentPadding = PaddingValues(16.dp),
//                text = stringResource(R.string.login_continue),
//                onClick = {
//                    if (activity != null){
//                        onAction(LoginAction.OnContinueClick(activity))
//                    }else{
//                        println("Activity not found!")
//                    }
//                },
//                enabled = state.isPhoneNumberValid,
//                trailingIcon = {
//                    if (state.isVerifying){
//                        CircularProgressIndicator(
//                            modifier = Modifier.size(16.dp),
//                            strokeWidth = 2.dp,
//                            color = MaterialTheme.colorScheme.onPrimary,
//                        )
//                    }
//                }
//            )
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


    }

}

@Preview
@Composable
private fun Preview() {
    GongchaTheme {
        LoginScreen(
            state = LoginState(
                textFieldState = TextFieldState(),
                isPhoneNumberValid = true,
                isVerifying = true
            ),
            onAction = {}
        )
    }
}