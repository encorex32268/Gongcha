@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.lihan.menu.presentation.menu.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lihan.core.domain.model.Tea
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.menu.presentation.menu.preview.TeaPreview

@Composable
fun TeaCard(
    tea: Tea,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.background.copy(
                alpha = 0.5f
            )
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (tea.imageUrl != null) {
                AsyncImage(
                    model = tea.imageUrl,
                    modifier = Modifier
                        .size(120.dp)
                        .then(
                            if (sharedTransitionScope != null && animatedVisibilityScope != null){
                                with(sharedTransitionScope){
                                    Modifier
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState(key = "tea_image_${tea.id}"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        )
                                }
                            }else Modifier
                        ),
                    contentDescription = tea.nameZh,
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = tea.nameZh,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = tea.descriptionZh,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun TeaCardPreview() {
    GongchaTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                TeaCard(
                    tea = TeaPreview.teas.first(),
                    onClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}