package com.lihan.gongcha.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.auth.presentation.code_verify.CodeVerifyRoot
import com.lihan.auth.presentation.login.LoginRoot
import com.lihan.gongcha.navigation.AuthGraph
import com.lihan.gongcha.navigation.CodeVerifyRoute
import com.lihan.gongcha.navigation.LoginRoute

fun NavGraphBuilder.authGraph(
    navigateToMenu: () -> Unit,
    navController: NavController
) {
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginRoot(
                onNavigateToMenu = navigateToMenu,
                onNavigateToCodeVerify = {
                    navController.navigate(CodeVerifyRoute)
                }
            )
        }

        composable<CodeVerifyRoute> {
            CodeVerifyRoot(
                onNavigateToMenu = navigateToMenu
            )
        }
    }
}


