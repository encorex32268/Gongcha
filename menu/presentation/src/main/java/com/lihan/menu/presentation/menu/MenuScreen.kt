@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.menu.presentation.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.BackgroundGradient
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.ObserveAsEvents
import com.lihan.core.presentation.ui.components.NoticeCard
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.components.StoreCard
import com.lihan.menu.presentation.menu.components.TeaCard
import com.lihan.menu.presentation.menu.components.TeaTypeChips
import com.lihan.menu.presentation.menu.preview.StorePreview
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MenuRoot(
    navigateToSelectStore: () -> Unit,
    navigateToMember: () -> Unit,
    signOut: () -> Unit,
    navigateToTeaOrder: (storeId: String,teaId: String) -> Unit,
    viewModel: MenuViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null
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
            is MenuEvent.OnNavigateToTeaOrder -> {
                val storeId = state.store?.id?:return@ObserveAsEvents
                navigateToTeaOrder(
                    storeId,
                    uiEvent.teaId
                )
            }
            MenuEvent.OnSignOut -> signOut()
            MenuEvent.OnNavigateToSelectStore -> navigateToSelectStore()
        }
    }

    MenuScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onAction = { action ->
            when(action){
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
    snackbarHostState: SnackbarHostState,
    sharedTransitionScope: SharedTransitionScope?=null,
    animatedVisibilityScope: AnimatedVisibilityScope?=null
) {
    Scaffold(
        snackbarHost = { SnackbarHost(
            modifier = Modifier.navigationBarsPadding().padding(bottom = 56.dp),
            hostState = snackbarHostState) },
        modifier = Modifier.background(
            brush = BackgroundGradient
        ),
        containerColor = Color.Transparent,
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
                        Box(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clip(CircleShape)
                                .size(36.dp)
                                .clickable(
                                    onClick = {
                                        onAction(MenuAction.OnNavigateToMember)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            if (state.profileImageUrl != null){
                                AsyncImage(
                                    model = state.profileImageUrl,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(36.dp),
                                    contentDescription = null
                                )
                            }else{
                                Icon(
                                    imageVector = GongchaIcons.Member,
                                    contentDescription = stringResource(R.string.user)
                                )
                            }
                        }

                        if (state.userName == null){
                            Text(text = stringResource(R.string.user_greeting))
                        } else {
                            Text(text = stringResource(R.string.member_greeting,state.userName))
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onAction(MenuAction.OnShowSignOutDialog)
                        }
                    ) {
                        Icon(
                            imageVector = GongchaIcons.LogOut,
                            contentDescription = null
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
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 72.dp)
        ) {
            item(key = "tea_store") {
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
                            NoticeCard(
                                text = stringResource(R.string.notice)
                            )
                        }
                    }else{
                        PrimaryButton(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {
                                onAction(MenuAction.OnSelectStore)
                            },
                            text = stringResource(R.string.select_store)
                        )
                    }

                }
            }
            item(key = "tea_type_chips"){
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
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }

        if (state.isShowSignOutDialog){
            AlertDialog(
                onDismissRequest = {
                    onAction(MenuAction.OnDismissSignOutDialog)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onAction(MenuAction.OnSignOutDialogConfirmClick)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.sign_out),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onAction(MenuAction.OnDismissSignOutDialog)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cart_cancel),
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.sign_out))
                },
                text = {
                    Text(text = stringResource(R.string.sign_out_message))
                }
            )
        }

        if (state.isShowSelectStoreWarning){
            AlertDialog(
                onDismissRequest = {
                    onAction(MenuAction.OnDismissSelectStoreWarningDialog)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onAction(MenuAction.OnDismissSelectStoreWarningDialog)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.sure),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.select_store_warning))
                },
                text = {
                    Text(text = stringResource(R.string.select_store_message))
                }
            )
        }
    }


}

@PreviewLightDark
@Composable
private fun Preview() {
    GongchaTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                MenuScreen(
                    state = MenuState(
                        store = StorePreview.store
                    ),
                    onAction = {},
                    snackbarHostState = remember { SnackbarHostState() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}