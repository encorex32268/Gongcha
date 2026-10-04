@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.menu.presentation.menu

import androidx.compose.animation.animateBounds
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.menu.presentation.menu.components.StoreCard
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.components.NoticeCard
import com.lihan.menu.presentation.menu.components.TeaCard
import com.lihan.menu.presentation.menu.components.TeaType
import com.lihan.menu.presentation.menu.components.TeaTypeChips
import com.lihan.menu.presentation.menu.preview.StorePreview
import com.lihan.menu.presentation.menu.preview.TeaPreview
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MenuRoot(
    navigateToSelectStore: () -> Unit,
    navigateToMember: () -> Unit,
    viewModel: MenuViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val message = stringResource(R.string.please_pick_store)

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when(uiEvent){
            MenuEvent.OnSendStorePickWarning -> {
                //if showing , don't send message again
                if (snackbarHostState.currentSnackbarData == null) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = message
                        )
                    }
                }
            }
        }
    }

    MenuScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = { action ->
            when(action){
                MenuAction.OnSelectStore -> navigateToSelectStore()
                MenuAction.OnNavigateToMember -> navigateToMember()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onAction: (MenuAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(
            modifier = Modifier.navigationBarsPadding().padding(bottom = 56.dp),
            hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        if (state.userName == null){
                            Text(text = stringResource(R.string.user_greeting))
                        } else {
                            Text(text = state.userName)
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onAction(MenuAction.OnNavigateToMember)
                        }
                    ) {
                        Icon(
                            imageVector = GongchaIcons.User,
                            contentDescription = stringResource(R.string.user)
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ){
                    if (state.store != null){
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StoreCard(
                                storeButtonText = stringResource(R.string.change_store),
                                store = state.store,
                                onChangeStoreClick = {
                                    onAction(MenuAction.OnSelectStore)
                                }
                            )
                            NoticeCard()
                        }
                    }else{
                        val isShowingWarning = snackbarHostState.currentSnackbarData != null
                        val buttonScale by animateFloatAsState(
                            targetValue = if (isShowingWarning) 1.5f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "store_button_scale"
                        )
                        PrimaryButton(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .graphicsLayer{
                                scaleX = buttonScale
                                scaleY = buttonScale
                            },
                            onClick = {
                                onAction(MenuAction.OnSelectStore)
                            },
                            text = stringResource(R.string.select_store)
                        )
                    }

                }
            }
            item(
                key = "tea_type_chips"
            ){
                TeaTypeChips(
                    selectedTeaType = state.type,
                    onTypeSelect = {
                        onAction(MenuAction.OnTeaTypeSelect(it))
                    }
                )
            }
            items(
                items = state.teas,
                key = { it.id }
            ){
                TeaCard(
                    modifier = Modifier.animateItem(),
                    tea = it,
                    onClick = {
                        onAction(MenuAction.OnTeaClick(it))
                    }
                )
            }


        }
    }


}

@Preview
@Composable
private fun Preview() {
    GongchaTheme {
        MenuScreen(
            state = MenuState(
                store = StorePreview.store
            ),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}