package com.lihan.gongcha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.R as DesignR
import com.lihan.gongcha.navigation.AppBottomBar
import com.lihan.gongcha.navigation.AppState
import com.lihan.gongcha.navigation.HistoryRoute
import com.lihan.gongcha.navigation.MemberRoute
import com.lihan.gongcha.navigation.MenuRoute
import com.lihan.gongcha.navigation.SelectStoreRoute
import com.lihan.gongcha.navigation.ShoppingCartRoute
import com.lihan.gongcha.navigation.TopLevelDestination
import com.lihan.gongcha.navigation.rememberAppState
import com.lihan.menu.menu.MenuRoot
import com.lihan.menu.store.StoreRoot

@Composable
fun NavRoot(
    appState: AppState = rememberAppState()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = appState.navController,
            startDestination = MenuRoute
        ) {
            // Top Level Destinations
            composable<MenuRoute> {
                MenuRoot(
                    navigateToSelectStore = {
                        appState.navController.navigate(SelectStoreRoute)
                    },
                    navigateToMember = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.MEMBER)
                    }
                )
            }
            composable<ShoppingCartRoute> {
                ShoppingCartPlaceholderScreen()
            }

            composable<HistoryRoute> {
                HistoryPlaceholderScreen()
            }

            composable<MemberRoute> {
                MemberPlaceholderScreen()
            }

            // Other Screens (BottomBar will automatically hide)
            composable<SelectStoreRoute> {
                StoreRoot(
                    onBack = {
                        appState.navController.navigateUp()
                    }
                )
            }
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
                currentDestination = appState.currentDestination
            )
        }
    }
}

@Composable
private fun ShoppingCartPlaceholderScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = GongchaIcons.ShoppingCart,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "購物車",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun HistoryPlaceholderScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = GongchaIcons.ListClock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "歷史紀錄",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MemberPlaceholderScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = GongchaIcons.Member,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "會員中心",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
