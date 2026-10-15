package com.lihan.gongcha.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.cart.presentation.CartRoot
import com.lihan.gongcha.navigation.ShoppingCartGraph
import com.lihan.gongcha.navigation.ShoppingCartRoute

fun NavGraphBuilder.shoppingCartGraph() {
    navigation<ShoppingCartGraph>(startDestination = ShoppingCartRoute) {
        composable<ShoppingCartRoute> {
            CartRoot()
        }
    }
}
