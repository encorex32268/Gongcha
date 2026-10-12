package com.lihan.gongcha.navigation.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.gongcha.navigation.HistoryGraph
import com.lihan.gongcha.navigation.HistoryRoute
import com.lihan.history.presentation.history.HistoryRoot

fun NavGraphBuilder.historyGraph(
    navController: NavController
) {
    navigation<HistoryGraph>(startDestination = HistoryRoute) {
        composable<HistoryRoute> {
            HistoryRoot()
        }
    }
}
