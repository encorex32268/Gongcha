@file:OptIn(InternalCoroutinesApi::class)

package com.lihan.core.network

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.lihan.core.domain.model.BusinessHour
import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.OrderStatus
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.model.User
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result
import com.lihan.core.network.mapper.toHistoryDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds


class FirebaseRemoteDataSource: RemoteDataSource {

    private val firebaseStore = FirebaseFirestore.getInstance()

    companion object {
        private const val DOC_TEAS = "teas"
        private const val DOC_STORES = "stores"
        private const val DOC_VERSIONS = "versions"
        private const val DOC_ORDERS = "orders"
        private const val DOC_USERS = "users"
        private const val DOC_USERS_ORDERS = "orders"
    }

    override suspend fun getTeas(): Result<List<Tea>, DataError> {
        return safeFirebaseCall(
            action = {
                val result = firebaseStore
                    .collection(DOC_TEAS)
                    .get()
                    .await()

                result.mapNotNull { it.toTea() }
            }
        )
    }

    override suspend fun getStores(): Result<List<Store>, DataError> {
        return safeFirebaseCall(
            action = {
                val result = firebaseStore
                    .collection(DOC_STORES)
                    .get()
                    .await()

                result.mapNotNull { it.toStore() }
            }
        )
    }

    override suspend fun getVersionCode(versionKey: String): Result<Int, DataError> {
        return safeFirebaseCall(
            action = {
                val result = firebaseStore
                    .collection(DOC_VERSIONS)
                    .get()
                    .await()

                val doc = result.documents.firstOrNull()
                doc?.getLong(versionKey)?.toInt() ?: 0
            }
        )
    }

    override suspend fun getHistories(userId: String): Result<List<History>, DataError>{
        return safeFirebaseCall(
            action = {
                val result = firebaseStore
                    .collection(DOC_USERS)
                    .document(userId)
                    .collection(DOC_USERS_ORDERS)
                    .get()
                    .await()

                result.mapNotNull { it.toHistory() }
            }
        )
    }


    override suspend fun checkout(
        userId: String,
        cartItems: List<CartItem>
    ): EmptyResult<DataError>{
        return safeFirebaseCall {
            withTimeoutOrNull(8.seconds){
                val docRef = firebaseStore
                    .collection(DOC_USERS)
                    .document(userId)
                    .collection(DOC_USERS_ORDERS)
                    .document()


                val historyDto = cartItems.toHistoryDto(docRef.id)

                docRef
                    .set(historyDto)
                    .await()
            }
        }
    }

    override suspend fun createUser(user: User): EmptyResult<DataError> {
        return safeFirebaseCall(
            action = {
                val docRef = firebaseStore
                    .collection(DOC_USERS)
                    .document(user.id)

                docRef
                    .set(user)
                    .await()
            }
        )
    }

    override suspend fun getUserById(id: String): Result<User?, DataError>{
        return safeFirebaseCall(
            action = {
                val docRef = firebaseStore
                    .collection(DOC_USERS)
                    .document(id)
                val result = docRef
                    .get()
                    .await()

                result.toObject(User::class.java)
            }
        )
    }

    override suspend fun cancelOrder(
        userId: String,
        orderId: String
    ): EmptyResult<DataError> {
        return safeFirebaseCall(
            action = {
                firebaseStore
                    .collection(DOC_USERS)
                    .document(userId)
                    .collection(DOC_USERS_ORDERS)
                    .document(orderId)
                    .delete()
                    .await()
            }
        )
    }


    suspend inline fun <T> safeFirebaseCall(
        crossinline action: suspend () -> T
    ): Result<T, DataError> = try {
        Result.Success(action())
    } catch (e: CancellationException) {
        throw e
    } catch (e: FirebaseFirestoreException) {
        val error = when (e.code) {
            FirebaseFirestoreException.Code.UNAVAILABLE -> DataError.Network.NO_INTERNET
            FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> DataError.Network.REQUEST_TIMEOUT
            FirebaseFirestoreException.Code.PERMISSION_DENIED -> DataError.Network.UNAUTHORIZED
            FirebaseFirestoreException.Code.NOT_FOUND -> DataError.Network.NOT_FOUND
            FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> DataError.Network.TOO_MANY_REQUESTS
            FirebaseFirestoreException.Code.CANCELLED -> DataError.Network.CANCELED
            else -> DataError.Network.SERVER_ERROR
        }
        Result.Error(error)
    } catch (e: FirebaseNetworkException) {
        Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: Exception) {
        Result.Error(DataError.Network.UNKNOWN)
    }

    private fun QueryDocumentSnapshot.toHistory(): History? {
        return try {
            val id = this.getString("id")?:""
            val createAt = this.getLong("createAt")?:0L
            val orderCode = this.getString("orderCode")?:""
            val status = this.getString("status")?:""
            val totalPrice = this.getLong("totalPrice")?.toInt()?:0
            val teas = this.get("teas") as? List<String>?: emptyList()

            History(
                id = id,
                createAt = createAt,
                orderCode = orderCode,
                status = OrderStatus.valueOf(status),
                totalPrice = totalPrice,
                teas = teas
            )
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


    private fun QueryDocumentSnapshot.toTea(): Tea? {
        return try {
            val descriptionCh = this.getString("descriptionCh")?:""
            val descriptionEn = this.getString("descriptionEn")?:""
            val imageUrl = this.getString("image_url")
            val nameEn = this.getString("name_en")?:""
            val nameZh = this.getString("name_zh")?:""
            val type = this.getString("type")?:""
            val price = this.get("price") as? Map<String, Long>?:emptyMap()
            val lPrice = price["L"] ?:0
            val mPrice = price["M"] ?:0
            if (lPrice == 0L || mPrice == 0L){
                return null
            }
            Tea(
                id = this.id,
                nameZh = nameZh,
                nameEn = nameEn,
                imageUrl = imageUrl,
                price = mPrice.toInt(),
                priceM = mPrice.toInt(),
                priceL = lPrice.toInt(),
                descriptionEn = descriptionEn,
                descriptionZh = descriptionCh,
                type = type
            )

        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    private fun QueryDocumentSnapshot.toStore(): Store? {
        return try {
            val address = this.getString("address") ?: ""
            val displayHours = this.getString("displayHours") ?: ""
            val name = this.getString("name") ?: ""
            val phone = this.getString("phone") ?: this.getString("phoneNumber") ?: ""
            val region = this.getString("region") ?: ""
            val businessHoursRaw = this.get("businessHours") as? List<Map<String, Any>> ?: emptyList()
            val businessHours = businessHoursRaw.mapNotNull { item ->
                val day = (item["day"] as? Number)?.toInt() ?: return@mapNotNull null
                val open = item["open"] as? String ?: ""
                val close = item["close"] as? String ?: ""
                BusinessHour(
                    day = day,
                    open = open,
                    close = close
                )
            }
            Store(
                id = this.id,
                region = region,
                name = name,
                address = address,
                displayHours = displayHours,
                phoneNumber = phone,
                businessHours = businessHours
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}