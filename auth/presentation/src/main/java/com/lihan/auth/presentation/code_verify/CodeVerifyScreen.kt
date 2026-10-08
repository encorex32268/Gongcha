package com.lihan.auth.presentation.code_verify

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.auth.presentation.R
import com.lihan.auth.presentation.code_verify.components.CodeTextField
import com.lihan.auth.presentation.login.LoginAction
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.core.presentation.ui.UiText
import org.koin.androidx.compose.koinViewModel

@Composable
fun CodeVerifyRoot(
    onNavigateToMenu: () -> Unit,
    viewModel: CodeVerifyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            CodeVerifyEvent.OnNavigateToMenu -> onNavigateToMenu()
        }
    }
    CodeVerifyScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CodeVerifyScreen(
    state: CodeVerifyState,
    onAction: (CodeVerifyAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val activity = LocalActivity.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.textFieldStates.forEachIndexed { index, textFieldState ->
                    CodeTextField(
                        modifier = Modifier.weight(1f),
                        textFieldState = textFieldState,
                        onDoneClick = {
                            if (index == state.textFieldStates.lastIndex){
                                focusManager.clearFocus()
                            }else{
                                focusManager.moveFocus(
                                    focusDirection = FocusDirection.Next
                                )
                            }
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ){
                if (state.isCountDownTimer){
                    Text(
                        text = state.timerString,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }else{
                    TextButton(
                        onClick = {
                            onAction(CodeVerifyAction.OnResendSMSCode(activity))
                        }
                    ){
                        Text(
                            text = stringResource(R.string.resend),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

            }
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp),
                text = stringResource(R.string.login_continue),
                onClick = {
                    onAction(CodeVerifyAction.OnConfirmClick(activity))
                },
                enabled = state.isValid
            )
            Spacer(Modifier.height(4.dp))
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(CodeVerifyAction.OnWithoutSigningClick)
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
        CodeVerifyScreen(
            state = CodeVerifyState(
                isVerifyCodeTimeout = false,
                isCountDownTimer = true
            ),
            onAction = {}
        )
    }
}