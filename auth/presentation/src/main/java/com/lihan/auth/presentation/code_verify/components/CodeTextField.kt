package com.lihan.auth.presentation.code_verify.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import kotlinx.coroutines.flow.drop

@Composable
fun CodeTextField(
    textFieldState: TextFieldState,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .drop(1) // 忽略初始值
            .collect { text ->
                if (text.isNotEmpty()) {
                    onDoneClick()
                }
            }
    }

    BasicTextField(
        interactionSource = interactionSource,
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = if (isFocused){
                    MaterialTheme.colorScheme.primary
                }else MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .padding(8.dp),
        state = textFieldState,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            onDoneClick()
        },
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            textAlign = TextAlign.Center
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = ReplacementTransformation(),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorator = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center
            ){
                if (textFieldState.text.toString().isEmpty()){
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                innerTextField()
            }
        },
    )

}


@Preview(showBackground = true)
@Composable
private fun CodeTextFieldPreview() {
    GongchaTheme {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(5){
                CodeTextField(
                    modifier = Modifier.weight(1f)
                        .aspectRatio(1f),
                    onDoneClick = {},
                    textFieldState = TextFieldState("1")
                )
            }
        }
    }
}