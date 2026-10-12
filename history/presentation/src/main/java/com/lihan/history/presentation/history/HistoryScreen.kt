@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.history.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.components.NoticeCard
import com.lihan.history.presentation.R
import com.lihan.history.presentation.history.components.HistoryItemCard
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@Composable
fun HistoryRoot(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {
        viewModel.onAction(HistoryAction.OnSyncHistories)
        onPauseOrDispose { }
    }

    HistoryScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(
                    stringResource(R.string.history_title)
                ) },
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            if (!state.isLogin){
                NoticeCard(
                    text = stringResource(R.string.history_notice)
                )
                Spacer(Modifier.height(16.dp))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.items){ history ->
                    HistoryItemCard(
                        history = history,
                        modifier = Modifier.fillMaxWidth(),
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
                        orderCode = Clock.System.now().plus(it.minutes).toEpochMilliseconds().toString().takeLast(6),
                        totalPrice = 100,
                        teas = listOf(
                            "1 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "2 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "3 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "4 x 仙草奶凍 (M·LITTLE·LITTLE)",
                            "5 x 仙草奶凍 (M·LITTLE·LITTLE)",
                        )
                    )
                }
            ),
            onAction = {}
        )
    }
}