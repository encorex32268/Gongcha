package com.lihan.core.database

import com.lihan.core.database.converter.Converters
import com.lihan.core.domain.model.BusinessHour
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun testBusinessHourListConversion() {
        val originalList = listOf(
            BusinessHour(day = 1, open = "10:00", close = "21:00"),
            BusinessHour(day = 2, open = "11:00", close = "22:00")
        )

        val jsonString = converters.fromBusinessHourList(originalList)
        assertNotNull(jsonString)

        val convertedList = converters.toBusinessHourList(jsonString)
        assertEquals(2, convertedList.size)
        assertEquals(1, convertedList[0].day)
        assertEquals("10:00", convertedList[0].open)
        assertEquals("21:00", convertedList[0].close)
        assertEquals(2, convertedList[1].day)
        assertEquals("11:00", convertedList[1].open)
        assertEquals("22:00", convertedList[1].close)
    }

    @Test
    fun testNullAndEmptyHandling() {
        val nullResult = converters.fromBusinessHourList(null)
        assertEquals(null, nullResult)

        val emptyListResult = converters.toBusinessHourList(null)
        assertTrue(emptyListResult.isEmpty())

        val invalidJsonResult = converters.toBusinessHourList("invalid-json")
        assertTrue(invalidJsonResult.isEmpty())
    }
}
