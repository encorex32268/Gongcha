package com.lihan.gongcha.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme

@Composable
fun AppBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    cartItemsCount: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // 橢圓形（Capsule / Pill）外觀
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 8.dp,
            tonalElevation = 3.dp,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                destinations.forEach { destination ->
                    val selected = currentDestination?.hasRoute(destination.route) == true

                    // 選中時變為 Primary 色，未選中為 onSurfaceVariant
                    val itemColor by animateColorAsState(
                        targetValue = if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        label = "bottom_bar_item_color"
                    )

                    val interactionSource = remember { MutableInteractionSource() }

                    BadgedBox(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onNavigateToDestination(destination)
                            },
                        badge = {
                            if (destination == TopLevelDestination.SHOPPING_CART && cartItemsCount > 0){
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ){
                                    Text(
                                        text = if(cartItemsCount > 99){
                                            "99+"
                                        }else cartItemsCount.toString(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        },
                        content = {
                            Icon(
                                imageVector = ImageVector.vectorResource(destination.iconRes),
                                contentDescription = destination.title.asString(),
                                tint = itemColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppBottomBarPreview() {
   GongchaTheme {
        AppBottomBar(
            cartItemsCount = 999,
            destinations = TopLevelDestination.entries,
            onNavigateToDestination = {},
            currentDestination = NavDestination(
                TopLevelDestination.MEMBER.name
            )
        )
    }
}
