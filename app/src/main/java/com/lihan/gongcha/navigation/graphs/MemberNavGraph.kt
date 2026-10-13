package com.lihan.gongcha.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.gongcha.navigation.MemberGraph
import com.lihan.gongcha.navigation.MemberRoute
import com.lihan.member.presentation.member.MemberRoot

fun NavGraphBuilder.memberGraph(
    navController: NavController
) {
    navigation<MemberGraph>(startDestination = MemberRoute) {
        composable<MemberRoute> {
            MemberRoot()
        }
    }
}


