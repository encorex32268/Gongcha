package com.lihan.gongcha

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lihan.menu.presentation.menu.MenuRoot
import com.lihan.menu.presentation.menu.MenuScreen

@Composable
fun NavRoot(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = "feature_menu"
    ){
        navigation(
            startDestination = "Menu",
            route = "feature_menu"
        ){
            composable(route = "Menu"){
                MenuRoot()
            }
        }
    }
}
