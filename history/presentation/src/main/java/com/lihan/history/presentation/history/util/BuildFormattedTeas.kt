package com.lihan.history.presentation.history.util

import androidx.compose.runtime.Composable
import com.lihan.core.domain.model.Ice
import com.lihan.core.domain.model.Size
import com.lihan.core.domain.model.Sugar
import com.lihan.core.presentation.ui.tea_order.toMLStringResource
import com.lihan.core.presentation.ui.tea_order.toStringResource

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