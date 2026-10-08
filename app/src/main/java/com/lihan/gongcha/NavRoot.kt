package com.lihan.gongcha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.lihan.gongcha.navigation.AppBottomBar
import com.lihan.gongcha.navigation.AppState
import com.lihan.gongcha.navigation.TopLevelDestination
import com.lihan.gongcha.navigation.graphs.authGraph
import com.lihan.gongcha.navigation.graphs.historyGraph
import com.lihan.gongcha.navigation.graphs.memberGraph
import com.lihan.gongcha.navigation.graphs.menuGraph
import com.lihan.gongcha.navigation.graphs.shoppingCartGraph
import com.lihan.gongcha.navigation.rememberAppState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavRoot(
    state: MainState,
    appState: AppState = rememberAppState()
) {
    SharedTransitionLayout {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = appState.navController,
                startDestination = state.startDestination
            ) {
                authGraph(
                    navController = appState.navController,
                    navigateToMenu = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.MENU)
                    }
                )
                // Menu Graph (Scoped SharedTransitionScope for tea image enlargement)
                menuGraph(
                    navController = appState.navController,
                    navigateToMember = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.MEMBER)
                    },
                    navigateToCart = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.SHOPPING_CART)
                    },
                    sharedTransitionScope = this@SharedTransitionLayout
                )
                shoppingCartGraph(
                    navController = appState.navController
                )
                historyGraph(
                    navController = appState.navController
                )
                memberGraph(
                    navController = appState.navController
                )
            }
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(8.dp),
                visible = appState.shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                AppBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    cartItemsCount = state.cartItemCount
                )
            }
        }
    }
}
