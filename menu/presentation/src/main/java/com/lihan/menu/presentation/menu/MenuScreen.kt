@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.menu.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.lihan.menu.presentation.menu.components.StoreCard
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.components.NoticeCard
import com.lihan.menu.presentation.menu.components.TeaCard
import com.lihan.menu.presentation.menu.components.TeaType
import com.lihan.menu.presentation.menu.components.TeaTypeChips
import com.lihan.menu.presentation.menu.preview.StorePreview
import com.lihan.menu.presentation.menu.preview.TeaPreview


@Composable
fun MenuRoot(
    viewModel: MenuViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MenuScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onAction: (MenuAction) -> Unit,
) {
    Scaffold(
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
                        onClick = {}
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
                .padding(12.dp)
        ) {
            item {
                if (state.store != null){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StoreCard(
                            store = state.store,
                            onChangeStoreClick = {
                                onAction(MenuAction.OnSelectStore)
                            }
                        )
                        NoticeCard()
                    }
                }else{
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        PrimaryButton(
                            onClick = {
                                onAction(MenuAction.OnSelectStore)
                            },
                            text = stringResource(R.string.select_store)
                        )
                    }
                }
            }
            item{
                TeaTypeChips(
                    selectedTeaType = state.type,
                    onTypeSelect = {
                        onAction(MenuAction.OnTeaTypeSelect(it))
                    }
                )
            }
            items(
                items = TeaPreview.teas,
                key = { it.id }
            ){
                TeaCard(
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
            onAction = {}
        )
    }
}