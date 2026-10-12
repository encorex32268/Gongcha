package com.lihan.core.presentation.designsystem.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
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

    val Plus: ImageVector
        @Composable
        get() =ImageVector.vectorResource(R.drawable.plus)

    val Minus: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.minus)

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

    val ShoppingCart: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.shopping_cart)

    val Check: ImageVector
        @Composable
        get() = Icons.Rounded.Check

    val Delete: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.trash)

    val Contactless: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.contactless)

    val Close: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.close)

    val LogOut: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.log_out)
}
