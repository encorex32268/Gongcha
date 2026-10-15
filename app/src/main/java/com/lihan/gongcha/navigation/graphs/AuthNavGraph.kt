package com.lihan.gongcha.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.auth.presentation.login.LoginRoot
import com.lihan.gongcha.navigation.AuthGraph
import com.lihan.gongcha.navigation.LoginRoute

fun NavGraphBuilder.authGraph(
    navigateToMenu: () -> Unit
) {
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginRoot(
                onNavigateToMenu = navigateToMenu,
            )
        }
    }
}


