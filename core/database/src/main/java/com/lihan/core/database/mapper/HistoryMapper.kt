package com.lihan.core.database.mapper

import com.lihan.core.database.entity.HistoryEntity
import com.lihan.core.domain.model.History

fun HistoryEntity.toHistory(): History = History(
    id = id,
    createAt = createAt,
    status = status,
    orderCode = orderCode,
    totalPrice = totalPrice,
    teas = teas
)

fun History.toHistoryEntity(userId: String = ""): HistoryEntity = HistoryEntity(
    id = id,
    userId = userId,
    createAt = createAt,
    status = status,
    orderCode = orderCode,
    totalPrice = totalPrice,
    teas = teas
)
