package com.lihan.core.database

import com.lihan.core.database.mapper.toStore
import com.lihan.core.database.mapper.toStoreEntity
import com.lihan.core.database.mapper.toTea
import com.lihan.core.database.mapper.toTeaEntity
import com.lihan.core.domain.model.BusinessHour
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun testTeaEntityMapping() {
        val domain = Tea(
            id = "tea_1",
            nameZh = "奶茶",
            nameEn = "Milk Tea",
            imageUrl = "https://example.com/tea.png",
            price = 50,
            priceM = 45,
            priceL = 55,
            descriptionZh = "好喝",
            descriptionEn = "Delicious",
            type = "milk_tea"
        )

        val entity = domain.toTeaEntity()
        assertEquals("tea_1", entity.id)
        assertEquals("奶茶", entity.nameZh)
        assertEquals("Milk Tea", entity.nameEn)
        assertEquals("https://example.com/tea.png", entity.imageUrl)
        assertEquals(50, entity.price)
        assertEquals(45, entity.priceM)
        assertEquals(55, entity.priceL)
        assertEquals("好喝", entity.descriptionZh)
        assertEquals("Delicious", entity.descriptionEn)
        assertEquals("milk_tea", entity.type)

        val mappedBack = entity.toTea()
        assertEquals(domain, mappedBack)
    }

    @Test
    fun testStoreEntityMapping() {
        val domain = Store(
            id = "store_1",
            region = "北區",
            name = "慶城店",
            address = "台北市慶城街",
            displayHours = "11:00-21:00",
            phoneNumber = "02-1234-5678",
            businessHours = listOf(
                BusinessHour(day = 1, open = "11:00", close = "21:00")
            )
        )

        val entity = domain.toStoreEntity()
        assertEquals("store_1", entity.id)
        assertEquals("北區", entity.region)
        assertEquals("慶城店", entity.name)
        assertEquals("台北市慶城街", entity.address)
        assertEquals("11:00-21:00", entity.displayHours)
        assertEquals("02-1234-5678", entity.phoneNumber)
        assertEquals(1, entity.businessHours.size)

        val mappedBack = entity.toStore()
        assertEquals(domain, mappedBack)
    }
}
