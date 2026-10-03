package com.lihan.core.database.mapper

import com.lihan.core.database.entity.TeaEntity
import com.lihan.core.domain.model.Tea

fun TeaEntity.toTea(): Tea = Tea(
    id = id,
    nameZh = nameZh,
    nameEn = nameEn,
    imageUrl = imageUrl,
    price = price,
    priceM = priceM,
    priceL = priceL,
    descriptionZh = descriptionZh,
    descriptionEn = descriptionEn,
    type = type
)

fun Tea.toTeaEntity(): TeaEntity = TeaEntity(
    id = id,
    nameZh = nameZh,
    nameEn = nameEn,
    imageUrl = imageUrl,
    price = price,
    priceM = priceM,
    priceL = priceL,
    descriptionZh = descriptionZh,
    descriptionEn = descriptionEn,
    type = type
)
