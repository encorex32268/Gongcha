package com.lihan.gongcha.navigation.graphs

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.gongcha.navigation.MenuGraph
import com.lihan.gongcha.navigation.MenuRoute
import com.lihan.gongcha.navigation.SelectStoreRoute
import com.lihan.gongcha.navigation.TeaOrderRoute
import com.lihan.menu.presentation.menu.MenuRoot
import com.lihan.menu.presentation.store.StoreRoot
import com.lihan.menu.presentation.tea_order.TeaOrderRoot

fun NavGraphBuilder.menuGraph(
    navController: NavController,
    navigateToMember: () -> Unit,
    navigateToCart: () -> Unit,
    sharedTransitionScope: SharedTransitionScope
) {
    navigation<MenuGraph>(startDestination = MenuRoute) {
        composable<MenuRoute>{
            MenuRoot(
                navigateToSelectStore = {
                    navController.navigate(SelectStoreRoute)
                },
                navigateToMember = navigateToMember,
                navigateToTeaOrder = { teaId ->
                    navController.navigate(TeaOrderRoute(teaId = teaId))
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }

        composable<SelectStoreRoute> {
            StoreRoot(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<TeaOrderRoute> {
            TeaOrderRoot(
                onBack = {
                    navController.navigateUp()
                },
                onNavigateToCart = navigateToCart,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this
            )
        }
    }
}
