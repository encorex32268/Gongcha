@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.lihan.menu.presentation.tea_order

import androidx.activity.BackEventCompat
import androidx.activity.compose.PredictiveBackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lihan.core.presentation.designsystem.AppBackgroundScaffold
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.core.presentation.ui.components.Counter
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.preview.TeaPreview
import com.lihan.menu.presentation.tea_order.components.IceSelection
import com.lihan.menu.presentation.tea_order.components.SectionCard
import com.lihan.menu.presentation.tea_order.components.SizeSelection
import com.lihan.menu.presentation.tea_order.components.SugarSelection
import com.lihan.menu.presentation.tea_order.components.ToppingSelection
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun TeaOrderRoot(
    onBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null,
    viewModel: TeaOrderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            TeaOrderEvent.OnSavedCartItem -> onNavigateToCart()
        }
    }

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
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null
) {
    val tea = state.tea

    // Handle back event
    PredictiveBackHandler { progress: Flow<BackEventCompat> ->
        try {
            progress.collect { backEvent ->
                onAction(TeaOrderAction.OnNavigateBack)
            }
        } catch (e: CancellationException) {
            throw e
        }
    }

    AppBackgroundScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (tea != null) {
                            Text(
                                modifier = Modifier.basicMarquee(),
                                text = tea.nameZh,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (tea.nameEn.isNotBlank()) {
                                Text(
                                    modifier = Modifier.basicMarquee(),
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
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(12.dp),
                text = stringResource(R.string.checkout,state.totalPrice.toString()),
                onClick = {
                    onAction(TeaOrderAction.OnAddToCartClick)
                },
                enabled = state.canCheckout,
                contentPadding = PaddingValues(12.dp)
            )
        }
    ){ paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Counter(
                        iconButtonSize = 36.dp,
                        textSize = 24.sp,
                        count = state.count,
                        onPlusClick = {
                            onAction(TeaOrderAction.OnPlusClick)
                        },
                        onMinusClick = {
                            onAction(TeaOrderAction.OnMinusClick)
                        }
                    )
                }
            }
            item(key = "size_section") {
                SectionCard(
                    title = stringResource(R.string.order_size),
                    subTitle = stringResource(R.string.size),
                    content = {
                        SizeSelection(
                            currentSize = state.size,
                            priceM = state.tea?.priceM.toString(),
                            priceL = state.tea?.priceL.toString(),
                            onSizeClick = {
                                onAction(TeaOrderAction.OnSizeSelect(it))
                            }
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
                            selectingToppings = state.toppings,
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
                            currentSugar = state.sugar,
                            onSugarClick = { sugar ->
                                onAction(TeaOrderAction.OnSugarSelect(sugar))
                            }
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
                            onIceClick = {
                                onAction(TeaOrderAction.OnIceSelect(it))
                            },
                            currentIce = state.ice
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