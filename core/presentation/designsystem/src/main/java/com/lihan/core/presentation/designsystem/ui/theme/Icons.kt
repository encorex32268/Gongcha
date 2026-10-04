package com.lihan.core.presentation.designsystem.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.lihan.core.presentation.designsystem.R

object GongchaIcons {
    val User: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.user)

    val Location: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.location)


    val Phone: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.phone)

    val Calendar: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.calendar)

    val Info: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.info)

    val Add: ImageVector
        @Composable
        get() = Icons.Rounded.Add

    val DrinkMenu: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.drink_menu)

    val ListClock: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.list_clock)

    val Member: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.member)

    val ArrowBack: ImageVector
        @Composable
        get() = Icons.AutoMirrored.Filled.ArrowBack
}
