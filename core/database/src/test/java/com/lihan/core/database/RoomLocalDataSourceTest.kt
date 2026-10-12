package com.lihan.core.database

import com.lihan.core.database.dao.CartItemDao
import com.lihan.core.database.dao.HistoryDao
import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.HistoryEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import com.lihan.core.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RoomLocalDataSourceTest {

    private lateinit var fakeTeaDao: FakeTeaDao
    private lateinit var fakeStoreDao: FakeStoreDao
    private lateinit var fakeCartItemDao: FakeCartItemDao
    private lateinit var fakeHistoryDao: FakeHistoryDao
    private lateinit var localDataSource: RoomLocalDataSource

    @Before
    fun setUp() {
        fakeTeaDao = FakeTeaDao()
        fakeStoreDao = FakeStoreDao()
        fakeCartItemDao = FakeCartItemDao()
        fakeHistoryDao = FakeHistoryDao()
        localDataSource = RoomLocalDataSource(
            teaDao = fakeTeaDao,
            storeDao = fakeStoreDao,
            cartItemDao = fakeCartItemDao,
            historyDao = fakeHistoryDao
        )
    }

    @Test
    fun testGetHistoriesAndUpsert() = runBlocking {
        val testHistory = HistoryEntity(
            id = "order_1",
            userId = "user_1",
            createAt = 1000L,
            status = OrderStatus.IN_PROGRESS,
            orderCode = "123456",
            totalPrice = 120,
            teas = listOf("珍珠奶茶")
        )

        localDataSource.upsertHistories(listOf(testHistory))

        val allHistories = localDataSource.getHistories().first()
        assertEquals(1, allHistories.size)
        assertEquals("order_1", allHistories[0].id)

        val userHistories = localDataSource.getHistoriesByUserId("user_1").first()
        assertEquals(1, userHistories.size)
        assertEquals("order_1", userHistories[0].id)

        val emptyUserHistories = localDataSource.getHistoriesByUserId("other_user").first()
        assertEquals(0, emptyUserHistories.size)
    }

    @Test
    fun testDeleteAndClearHistories() = runBlocking {
        val testHistory1 = HistoryEntity(
            id = "order_1",
            userId = "user_1",
            createAt = 1000L,
            status = OrderStatus.IN_PROGRESS,
            orderCode = "123456",
            totalPrice = 120,
            teas = listOf("珍珠奶茶")
        )
        val testHistory2 = HistoryEntity(
            id = "order_2",
            userId = "user_1",
            createAt = 2000L,
            status = OrderStatus.COMPLETED,
            orderCode = "654321",
            totalPrice = 80,
            teas = listOf("四季春茶")
        )

        localDataSource.upsertHistories(listOf(testHistory1, testHistory2))
        assertEquals(2, localDataSource.getHistories().first().size)

        localDataSource.deleteHistoryById("order_1")
        val remaining = localDataSource.getHistories().first()
        assertEquals(1, remaining.size)
        assertEquals("order_2", remaining[0].id)

        localDataSource.clearHistoriesByUserId("user_1")
        assertEquals(0, localDataSource.getHistories().first().size)
    }

    private class FakeHistoryDao : HistoryDao {
        private val state = MutableStateFlow<List<HistoryEntity>>(emptyList())

        override fun getHistories(): Flow<List<HistoryEntity>> = state

        override fun getHistoriesByUserId(userId: String): Flow<List<HistoryEntity>> =
            state.map { list -> list.filter { it.userId == userId } }

        override suspend fun getHistoryById(id: String): HistoryEntity? =
            state.value.find { it.id == id }

        override suspend fun upsertHistories(histories: List<HistoryEntity>) {
            val currentMap = state.value.associateBy { it.id }.toMutableMap()
            histories.forEach { currentMap[it.id] = it }
            state.value = currentMap.values.toList()
        }

        override suspend fun upsertHistory(history: HistoryEntity) {
            upsertHistories(listOf(history))
        }

        override suspend fun deleteHistoryById(id: String) {
            state.value = state.value.filterNot { it.id == id }
        }

        override suspend fun clearHistoriesByUserId(userId: String) {
            state.value = state.value.filterNot { it.userId == userId }
        }

        override suspend fun clearHistories() {
            state.value = emptyList()
        }
    }

    private class FakeTeaDao : TeaDao {
        private val state = MutableStateFlow<List<TeaEntity>>(emptyList())
        override fun getTeas(): Flow<List<TeaEntity>> = state
        override suspend fun getTeaById(id: String): TeaEntity? = state.value.find { it.id == id }
        override suspend fun upsertTeas(teas: List<TeaEntity>) {
            val currentMap = state.value.associateBy { it.id }.toMutableMap()
            teas.forEach { currentMap[it.id] = it }
            state.value = currentMap.values.toList()
        }
        override suspend fun upsertTea(tea: TeaEntity) = upsertTeas(listOf(tea))
        override suspend fun clearTeas() { state.value = emptyList() }
    }

    private class FakeStoreDao : StoreDao {
        private val state = MutableStateFlow<List<StoreEntity>>(emptyList())
        override fun getStores(): Flow<List<StoreEntity>> = state
        override suspend fun getStoreById(id: String): StoreEntity? = state.value.find { it.id == id }
        override suspend fun upsertStores(stores: List<StoreEntity>) {
            val currentMap = state.value.associateBy { it.id }.toMutableMap()
            stores.forEach { currentMap[it.id] = it }
            state.value = currentMap.values.toList()
        }
        override suspend fun upsertStore(store: StoreEntity) = upsertStores(listOf(store))
        override suspend fun clearStores() { state.value = emptyList() }
    }

    private class FakeCartItemDao : CartItemDao {
        private val state = MutableStateFlow<List<CartItemEntity>>(emptyList())
        override fun getCartItems(): Flow<List<CartItemEntity>> = state
        override suspend fun deleteCartItem(id: Int) { state.value = state.value.filterNot { it.id == id } }
        override suspend fun clearCartItems() { state.value = emptyList() }
        override suspend fun upsertCartItem(cartItem: CartItemEntity) {
            state.value = state.value + cartItem
        }
        override fun getTotalItemCount(): Flow<Int> = state.map { it.sumOf { item -> item.count } }
        override suspend fun updateCartItemCount(cartItemId: Int, count: Int) {
            state.value = state.value.map { if (it.id == cartItemId) it.copy(count = count) else it }
        }
    }
}
