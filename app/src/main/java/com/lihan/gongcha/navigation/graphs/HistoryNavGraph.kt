package com.lihan.gongcha.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.gongcha.navigation.HistoryGraph
import com.lihan.gongcha.navigation.HistoryRoute
import com.lihan.history.presentation.history.HistoryRoot

fun NavGraphBuilder.historyGraph() {
    navigation<HistoryGraph>(startDestination = HistoryRoute) {
        composable<HistoryRoute> {
            HistoryRoot()
        }
    }
}
