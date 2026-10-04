@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.menu.presentation.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons.ArrowBack
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.components.StoreCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StoreRoot(
    onBack: () -> Unit,
    viewModel: StoreViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            StoreEvent.OnSavedSelectedStore -> onBack()
        }
    }
    StoreScreen(
        state = state,
        onAction = { action ->
            when(action){
                StoreAction.OnNavigateBack -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun StoreScreen(
    state: StoreState,
    onAction: (StoreAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    stringResource(R.string.select_store)
                ) },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(StoreAction.OnNavigateBack)
                    }) {
                        Icon(
                            imageVector = ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                state.stores,
                key = { it.id }
            ){  store ->
                StoreCard(
                    store = store,
                    storeButtonText = stringResource(R.string.select),
                    onChangeStoreClick = {
                        onAction(StoreAction.OnStoreSelected(store.id))
                    }
                )
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun StoreScreenPreview() {
    StoreScreen(
        state = StoreState(),
        onAction = {}
    )
}