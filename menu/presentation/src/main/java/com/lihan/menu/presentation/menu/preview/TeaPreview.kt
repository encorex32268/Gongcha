package com.lihan.menu.presentation.menu.preview

import com.lihan.core.domain.model.Tea
import com.lihan.menu.presentation.menu.components.TeaType

object TeaPreview {
    val teas = listOf(
        Tea(
            id = "gongcha_alisan_tea",
            nameZh = "四季春",
            nameEn = "Alisan Tea",
            imageUrl = "https://www.gong-cha.com.tw/uploads/signature-products/645b0e8a5ff7f.png",
            price = 35,
            priceM = 35,
            priceL = 100,
            type = TeaType.ORIGINAL.name,
            descriptionZh = "清揚奔放的輕發酵茶，茶香輕逸，入喉甜醇，讓人著迷的飽滿香氣。",
            descriptionEn = "A vibrant and uplifting lightly fermented tea with an airy floral aroma and a sweet, mellow finish, boasting a captivating, full-bodied fragrance."
        )
    )
}