package com.lihan.gongcha.navigation

import androidx.annotation.DrawableRes
import com.lihan.core.presentation.designsystem.R as DesignR
import com.lihan.core.presentation.ui.UiText
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route: KClass<*>,
    val startRoute: Any,
    @DrawableRes val iconRes: Int,
    val title: UiText
) {
    MENU(
        route = MenuRoute::class,
        startRoute = MenuRoute,
        iconRes = DesignR.drawable.drink_menu,
        title = UiText.StringResource(DesignR.string.nav_menu)
    ),
    SHOPPING_CART(
        route = ShoppingCartRoute::class,
        startRoute = ShoppingCartRoute,
        iconRes = DesignR.drawable.shopping_cart,
        title = UiText.StringResource(DesignR.string.nav_shopping_cart)
    ),
    HISTORY(
        route = HistoryRoute::class,
        startRoute = HistoryRoute,
        iconRes = DesignR.drawable.list_clock,
        title = UiText.StringResource(DesignR.string.nav_history)
    ),
    MEMBER(
        route = MemberRoute::class,
        startRoute = MemberRoute,
        iconRes = DesignR.drawable.user,
        title = UiText.StringResource(DesignR.string.nav_member)
    )
}
