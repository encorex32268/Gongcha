package com.lihan.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.auth.presentation.R
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.playstore_512),
                contentDescription = null
            )

            OutlinedTextField(
                state = state.textFieldState,
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                placeholder = {
                    Text(
                        text = "0912345678"
                    )
                },
                supportingText = {
                    Text(
                        text = "SupportText"
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.75f),
                contentPadding = PaddingValues(16.dp),
                text = stringResource(R.string.login_continue),
                onClick = {
                    onAction(LoginAction.OnContinueClick)
                },
                enabled = state.isPhoneNumberValid
            )
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
            state = LoginState(),
            onAction = {}
        )
    }
}