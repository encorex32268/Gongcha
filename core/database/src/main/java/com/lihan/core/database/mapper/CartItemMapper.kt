package com.lihan.core.database.mapper

import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.domain.model.CartItem

fun CartItemEntity.toCartItem(): CartItem = CartItem(
    id = id,
    teaId = teaId,
    teaName = teaName,
    imageUrl = imageUrl,
    storeName = storeName,
    size = size,
    toppings = toppings,
    sugar = sugar,
    ice = ice,
    count = count,
    price = price,
    userId = userId,
)

fun CartItem.toCartItemEntity(): CartItemEntity = CartItemEntity(
    userId = userId,
    teaId = teaId,
    teaName = teaName,
    imageUrl = imageUrl,
    storeName = storeName,
    size = size,
    toppings = toppings,
    sugar = sugar,
    ice = ice,
    count = count,
    price = price
)
