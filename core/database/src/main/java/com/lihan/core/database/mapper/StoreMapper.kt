package com.lihan.core.database.mapper

import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.domain.model.Store

fun StoreEntity.toStore(): Store = Store(
    id = id,
    region = region,
    name = name,
    address = address,
    displayHours = displayHours,
    phoneNumber = phoneNumber,
    businessHours = businessHours
)

fun Store.toStoreEntity(): StoreEntity = StoreEntity(
    id = id,
    region = region,
    name = name,
    address = address,
    displayHours = displayHours,
    phoneNumber = phoneNumber,
    businessHours = businessHours
)
