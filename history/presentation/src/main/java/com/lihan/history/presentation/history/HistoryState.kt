package com.lihan.history.presentation.history

import androidx.compose.runtime.Composable
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.Ice
import com.lihan.core.domain.model.Size
import com.lihan.core.domain.model.Sugar
import com.lihan.core.presentation.ui.tea_order.toMLStringResource
import com.lihan.core.presentation.ui.tea_order.toStringResource

data class HistoryState(
    val isLogin: Boolean = false,
    val items: List<History> = emptyList()
)

@Composable
fun List<String>.formattedTeas(): List<String>{
    return map { teaDescription ->
        val leftRoundBracket = teaDescription.indexOf("(")
        val rightRoundBracket = teaDescription.indexOf(")")
        //M·LITTLE·LITTLE
        val subString = teaDescription.substring(
            startIndex = leftRoundBracket,
            endIndex = rightRoundBracket
        ).replace("(","")
            .replace(")","")
            .split("·")

        val count = teaDescription.substringBefore(" x")
        val name = teaDescription
            .substringAfter("x ")
            .substringBefore("(")
        val size = Size.valueOf(subString[0]).toMLStringResource()
        val sugar = Sugar.valueOf(subString[1]).toStringResource()
        val ice = Ice.valueOf(subString[2]).toStringResource()

        "$count x $name ${size}·${sugar}·${ice}"
    }
}