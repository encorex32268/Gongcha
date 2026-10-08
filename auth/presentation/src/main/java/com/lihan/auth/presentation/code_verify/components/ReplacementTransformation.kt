@file:OptIn(ExperimentalFoundationApi::class)

package com.lihan.auth.presentation.code_verify.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class ReplacementTransformation: InputTransformation {
    override fun TextFieldBuffer.transformInput() {

        if (changes.changeCount == 0){
            return
        }
        val lastChangeRange = changes.getRange(changes.changeCount - 1)

        if (lastChangeRange.length > 0){

            val newChar = asCharSequence()
                .substring(lastChangeRange.start,lastChangeRange.end)
                .lastOrNull { it.isDigit() }

            if (newChar != null){
                replace(0,length,newChar.toString())
            }else{
                revertAllChanges()
            }
        }

    }
}