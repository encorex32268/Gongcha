package com.lihan.core.database

import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import com.lihan.core.database.repository.RoomGongchaRepository
import com.lihan.core.domain.model.BusinessHour
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RoomCartRepositoryTest {

    private lateinit var fakeTeaDao: FakeTeaDao
    private lateinit var fakeStoreDao: FakeStoreDao
    private lateinit var repository: RoomGongchaRepository

    @Before
    fun setUp() {
        fakeTeaDao = FakeTeaDao()
        fakeStoreDao = FakeStoreDao()
        repository = RoomGongchaRepository(fakeTeaDao, fakeStoreDao)
    }

    @Test
    fun testGetTeasAndUpsert() = runBlocking {
        val testTea = Tea(
            id = "t1",
            nameZh = "奶茶",
            nameEn = "Milk Tea",
            imageUrl = null,
            price = 50,
            priceM = 40,
            priceL = 50,
            descriptionZh = "好喝",
            descriptionEn = "Good",
            type = "milk"
        )

        repository.upsertTeas(listOf(testTea))

        val result = repository.getTeas().first()
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.size)
        assertEquals("奶茶", data[0].nameZh)
    }

    @Test
    fun testGetStoresAndUpsert() = runBlocking {
        val testStore = Store(
            id = "s1",
            region = "北區",
            name = "慶城店",
            address = "慶城街",
            displayHours = "11:00-21:00",
            phoneNumber = "02-1234-5678",
            businessHours = listOf(BusinessHour(1, "11:00", "21:00"))
        )

        repository.upsertStores(listOf(testStore))

        val result = repository.getStores().first()
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.size)
        assertEquals("慶城店", data[0].name)
    }

    private class FakeTeaDao : TeaDao {
        private val state = MutableStateFlow<List<TeaEntity>>(emptyList())

        override fun getTeas(): Flow<List<TeaEntity>> = state

        override suspend fun getTeaById(id: String): TeaEntity? =
            state.value.find { it.id == id }

        override suspend fun upsertTeas(teas: List<TeaEntity>) {
            val currentMap = state.value.associateBy { it.id }.toMutableMap()
            teas.forEach { currentMap[it.id] = it }
            state.value = currentMap.values.toList()
        }

        override suspend fun upsertTea(tea: TeaEntity) {
            upsertTeas(listOf(tea))
        }

        override suspend fun clearTeas() {
            state.value = emptyList()
        }
    }

    private class FakeStoreDao : StoreDao {
        private val state = MutableStateFlow<List<StoreEntity>>(emptyList())

        override fun getStores(): Flow<List<StoreEntity>> = state

        override suspend fun getStoreById(id: String): StoreEntity? =
            state.value.find { it.id == id }

        override suspend fun upsertStores(stores: List<StoreEntity>) {
            val currentMap = state.value.associateBy { it.id }.toMutableMap()
            stores.forEach { currentMap[it.id] = it }
            state.value = currentMap.values.toList()
        }

        override suspend fun upsertStore(store: StoreEntity) {
            upsertStores(listOf(store))
        }

        override suspend fun clearStores() {
            state.value = emptyList()
        }
    }
}
