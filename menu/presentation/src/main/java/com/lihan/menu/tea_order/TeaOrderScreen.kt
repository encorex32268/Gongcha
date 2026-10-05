@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.lihan.menu.tea_order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.menu.menu.preview.TeaPreview
import com.lihan.menu.presentation.R
import com.lihan.menu.tea_order.components.Ice
import com.lihan.menu.tea_order.components.IceSelection
import com.lihan.menu.tea_order.components.OrderBottomBar
import com.lihan.menu.tea_order.components.SectionCard
import com.lihan.menu.tea_order.components.Size
import com.lihan.menu.tea_order.components.SizeSelection
import com.lihan.menu.tea_order.components.Sugar
import com.lihan.menu.tea_order.components.SugarSelection
import com.lihan.menu.tea_order.components.Topping
import com.lihan.menu.tea_order.components.ToppingSelection
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
                else -> Unit
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
    val tea = state.tea
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
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
        },
        bottomBar = {
            OrderBottomBar(
                modifier = Modifier.padding(16.dp),
                count = 10,
                price = 10,
                onPlusClick = {
                    onAction(TeaOrderAction.OnPlusClick)
                },
                onMinusClick = {
                    onAction(TeaOrderAction.OnMinusClick)
                },
                onCheckoutClick = {
                    onAction(TeaOrderAction.OnCheckoutClick)
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            item {
                // Hero drink image enlarged with sharedElement transition
                if (tea?.imageUrl != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        AsyncImage(
                            model = tea.imageUrl,
                            contentDescription = tea.nameZh,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(260.dp)
                                .then(
                                    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                                        with(sharedTransitionScope) {
                                            Modifier
                                                .sharedElement(
                                                    sharedContentState = rememberSharedContentState(key = "tea_image_${tea.id}"),
                                                    animatedVisibilityScope = animatedVisibilityScope
                                                )
                                        }
                                    } else Modifier
                                )
                        )

                    }
                }
            }
            item(key = "size_section") {
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
            item(key = "toppings_section"){
                SectionCard(
                    title = stringResource(R.string.order_toppings),
                    subTitle = stringResource(R.string.toppings),
                    supportText = stringResource(R.string.can_multi_select),
                    content = {
                        ToppingSelection(
                            selectingToppings = listOf(
                                Topping.BROWN_SUGAR
                            ),
                            onToppingClick = {
                                onAction(TeaOrderAction.OnToppingClick(it))
                            }
                        )
                    }
                )
            }
            item(key = "sugar_section"){
                SectionCard(
                    title = stringResource(R.string.order_sugar),
                    subTitle = stringResource(R.string.sugar),
                    content = {
                        SugarSelection(
                            currentSugar = Sugar.LESS
                        )
                    }
                )
            }
            item(key = "ice_section"){
                SectionCard(
                    title = stringResource(R.string.order_ice),
                    subTitle = stringResource(R.string.ice),
                    content = {
                        IceSelection(
                            currentIce = Ice.REGULAR
                        )
                    }
                )
            }
            item {
                Spacer(Modifier.height(36.dp))
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