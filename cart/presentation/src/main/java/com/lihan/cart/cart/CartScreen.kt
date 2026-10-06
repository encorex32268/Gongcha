@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.cart.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.cart.cart.components.CartEmptyView
import com.lihan.cart.cart.components.CartItemCard
import com.lihan.cart.presentation.R
import com.lihan.core.domain.model.CartItem
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.model.Ice
import com.lihan.core.presentation.ui.model.Size
import com.lihan.core.presentation.ui.model.Sugar
import com.lihan.core.presentation.ui.model.Topping
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartRoot(
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CartScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CartScreen(
    state: CartState,
    onAction: (CartAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    stringResource(R.string.cart_title)
                ) }
            )
        }
    ) { padding ->
        when{
            state.cartItems.isEmpty() -> {
                CartEmptyView()
            }
            else ->{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    items(
                        items = state.cartItems,
                        key = { it.id!! }
                    ){  cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onPlusClick = {
                                onAction(CartAction.OnPlusClick(cartItem.id))
                            },
                            onMinusClick = {
                                onAction(CartAction.OnMinusClick(cartItem.id))
                            },
                            onDeleteClick = {
                                val deleteItemContent = "${cartItem.teaName} x ${cartItem.count}"
                                onAction(CartAction.OnShowDeleteDialog(deleteItemContent,cartItem.id))
                            }
                        )
                    }
                    item{
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }



    }
    if (state.deleteItemContent != null){
        AlertDialog(
            onDismissRequest = {
                onAction(CartAction.OnDismissDeleteDialog)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(CartAction.OnConfirmDeleteClick)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cart_delete),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onAction(CartAction.OnDismissDeleteDialog)
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
                Text(text = stringResource(R.string.delete))
            },
            text = {
                Text(text = stringResource(R.string.cart_delete_message,state.deleteItemContent))
            }
        )
    }

}

@Preview
@Composable
private fun Preview() {
    GongchaTheme {
        CartScreen(
            state = CartState(
                cartItems = (0..10).map {
                    CartItem(
                        id = it + 1,
                        teaId = it.toString(),
                        teaName = "茉香綠_${it}",
                        imageUrl = "",
                        storeName = "高雄市苓雅區光華一路230號",
                        size = Size.entries.random().name,
                        toppings = Topping.entries.shuffled().take(4).map { topping -> topping.name },
                        sugar = Sugar.entries.random().name,
                        ice = Ice.entries.random().name,
                        count = 100,
                        price = 100

                    )
                }
            ),
            onAction = {}
        )
    }
}