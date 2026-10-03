package com.lihan.menu.presentation.menu.preview

import com.lihan.core.domain.BusinessHour
import com.lihan.core.domain.Store

object StorePreview {
    val store = Store(
        id = "Tester12314",
        region = "南區",
        name = "中正民生店",
        address = "高雄市苓雅區光華一路230號",
        displayHours = "平日 09:00-19:00、假日 10:00-17:30",
        phoneNumber = "07-223-0193",
        businessHours = listOf(
            BusinessHour(day = 1, open = "09:00", close = "19:00"),
            BusinessHour(day = 2, open = "09:00", close = "19:00"),
            BusinessHour(day = 3, open = "09:00", close = "19:00"),
            BusinessHour(day = 4, open = "09:00", close = "19:00"),
            BusinessHour(day = 5, open = "09:00", close = "19:00"),
            BusinessHour(day = 6, open = "09:00", close = "19:00"),
            BusinessHour(day = 0, open = "09:00", close = "19:00"),
        )
    )
}