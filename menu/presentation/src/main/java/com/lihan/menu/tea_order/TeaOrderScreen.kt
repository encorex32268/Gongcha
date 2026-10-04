@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.lihan.menu.tea_order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.menu.menu.preview.TeaPreview
import com.lihan.menu.presentation.R
import com.lihan.menu.tea_order.components.SectionCard
import com.lihan.menu.tea_order.components.Size
import com.lihan.menu.tea_order.components.SizeSelection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeaOrderRoot(
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null,
    viewModel: TeaOrderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TeaOrderScreen(
        state = state,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onAction = { action ->
            when (action) {
                TeaOrderAction.OnNavigateBack -> onBack()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun TeaOrderScreen(
    state: TeaOrderState,
    onAction: (TeaOrderAction) -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tea_order_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(TeaOrderAction.OnNavigateBack) }) {
                        Icon(
                            imageVector = GongchaIcons.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        if (state.isLoading && state.tea == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val tea = state.tea
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hero drink image enlarged with sharedElement transition
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(350.dp)
                            .offset(y = (-180).dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.2f
                                ),
                                shape = CircleShape
                            )
                    )
                    if (tea?.imageUrl != null) {
                        AsyncImage(
                            model = tea.imageUrl,
                            contentDescription = tea.nameZh,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(260.dp)
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
                                )
                        )
                    }else{
                        Image(
                            painter = painterResource(R.drawable.gongcha_alisan_tea),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(250.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        if (tea != null) {
                            Text(
                                text = tea.nameZh,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (tea.nameEn.isNotBlank()) {
                                Text(
                                    text = tea.nameEn,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                SectionCard(
                    title = stringResource(R.string.order_size),
                    subTitle = stringResource(R.string.size),
                    content = {
                        SizeSelection(
                            currentSize = Size.L,
                            priceM = "",
                            priceL = ""
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TeaOrderScreenPreview() {
    GongchaTheme {
        TeaOrderScreen(
            state = TeaOrderState(
                tea = TeaPreview.teas.first().copy(
                    imageUrl = null
                )
            ),
            sharedTransitionScope = null,
            animatedVisibilityScope = null,
            onAction = {}
        )
    }
}