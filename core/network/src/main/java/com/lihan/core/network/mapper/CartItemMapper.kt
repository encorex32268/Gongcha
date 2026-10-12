package com.lihan.core.network.mapper

import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.network.model.HistoryDto
import kotlin.time.Clock

fun List<CartItem>.toHistoryDto(id: String): HistoryDto {
    val createAt = Clock.System.now().toEpochMilliseconds()
    val totalPrice = this.sumOf { cartItem ->
        (cartItem.price * cartItem.count)
    }
    return HistoryDto(
        id = id,
        createAt = createAt,
        status = OrderStatus.IN_ORDER.name,
        orderCode = createAt.toString().takeLast(6),
        totalPrice = totalPrice,
        teas = buildTeasString(this)
    )
}

private fun buildTeasString(cartItem: List<CartItem>): List<String>{
    return cartItem.map { cartItem ->
        "${cartItem.count} x ${cartItem.teaName} (${cartItem.size}·${cartItem.sugar}·${cartItem.ice})"
    }
}