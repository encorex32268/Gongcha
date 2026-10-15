@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.history.presentation.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.presentation.designsystem.AppBackgroundScaffold
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.core.presentation.ui.components.NoticeCard
import com.lihan.history.presentation.R
import com.lihan.history.presentation.history.components.HistoryItemCard
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@Composable
fun HistoryRoot(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val cancelOrderSuccessString = stringResource(R.string.history_cancel_order_success)

    LifecycleResumeEffect(Unit) {
        viewModel.onAction(HistoryAction.OnSyncHistories)
        onPauseOrDispose { }
    }

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is HistoryEvent.OnCancelOrderFailed -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = uiEvent.uiText.asString(context))
                }
            }

            HistoryEvent.OnCancelOrderSuccess -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = cancelOrderSuccessString)
                }
            }
        }
    }

    HistoryScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun HistoryScreen(
    state: HistoryState,
    snackbarHostState: SnackbarHostState,
    onAction: (HistoryAction) -> Unit,
) {
    AppBackgroundScaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 56.dp),
                hostState = snackbarHostState
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.history_title)
                    )
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = painterResource(R.drawable.hitstory_empty_image),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        AnimatedVisibility(visible = state.isSyncing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    item {
                        AnimatedVisibility(visible = !state.isLogin) {
                            NoticeCard(
                                text = stringResource(R.string.history_notice)
                            )
                        }
                    }
                    items(
                        items = state.items,
                        key = { it.orderCode }
                    ) { history ->
                        HistoryItemCard(
                            history = history,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            onOrderCancel = {
                                onAction(HistoryAction.OnOrderCancelClick(history.id))
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }


            }
        }
        if (state.isShowCancelOrderDialog){
            AlertDialog(
                onDismissRequest = {
                    onAction(HistoryAction.OnDismissCancelOrderDialog)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onAction(HistoryAction.OnConfirmCancelOrderClick)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.order_cancel_confirm),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onAction(HistoryAction.OnDismissCancelOrderDialog)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.order_cancel_cancel),
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.order_cancel_title))
                },
                text = {
                    Text(text = stringResource(R.string.order_cancel_message))
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    GongchaTheme {
        HistoryScreen(
            state = HistoryState(
                items = (0..10).map {
                    History(
                        id = "ID - ${it}",
                        createAt = Clock.System.now().plus(it.days).toEpochMilliseconds(),
                        status = OrderStatus.entries.random(),
                        orderCode = Clock.System.now().plus(it.minutes).toEpochMilliseconds()
                            .toString().takeLast(6),
                        totalPrice = 100,
                        teas = listOf(
                            "1 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "2 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "3 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "4 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "5 x 仙草奶凍 (M·LITTLE·LITTLE)",
                        )
                    )
                },
                isSyncing = false
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}