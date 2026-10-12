@file:OptIn(InternalCoroutinesApi::class)

package com.lihan.core.network

import com.google.firebase.firestore.FirebaseFirestore
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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.time.withTimeoutOrNull
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds
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

    override fun getTeas(): Flow<Result<List<Tea>, DataError>> = callbackFlow {
        firebaseStore
            .collection(DOC_TEAS)
            .get()
            .addOnFailureListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnCanceledListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnSuccessListener { result ->
                val remoteTeas = result.mapNotNull {
                    it.toTea()
                }
                if (remoteTeas.isEmpty()){
                    trySend(Result.Error(DataError.Network.SERIALIZATION))
                }else{
                    trySend(Result.Success(remoteTeas))
                }
            }

        awaitClose()

    }

    override fun getStores(): Flow<Result<List<Store>, DataError>> = callbackFlow {
        firebaseStore
            .collection(DOC_STORES)
            .get()
            .addOnFailureListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnCanceledListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnSuccessListener { result ->
                val remoteStores = result.mapNotNull {
                    it.toStore()
                }
                if (remoteStores.isEmpty()) {
                    trySend(Result.Error(DataError.Network.SERIALIZATION))
                } else {
                    trySend(Result.Success(remoteStores))
                }
            }

        awaitClose()
    }

    override fun getVersionCode(versionKey: String): Flow<Result<Int, DataError>> = callbackFlow{
        firebaseStore
            .collection(DOC_VERSIONS)
            .get()
            .addOnFailureListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnCanceledListener {
                trySend(Result.Error(DataError.Network.FIREBASE_GET_DATA_FAILED))
            }
            .addOnSuccessListener { result ->
                val doc = result.documents.firstOrNull()
                val version = doc?.getLong(versionKey)?.toInt() ?: 0
                trySend(Result.Success(version))
            }

        awaitClose()
    }

    override suspend fun getHistories(userId: String): Result<List<History>, DataError> =
        suspendCancellableCoroutine{ cancellableContinuation ->
            firebaseStore
                .collection(DOC_USERS)
                .document(userId)
                .collection(DOC_USERS_ORDERS)
                .get()
                .addOnFailureListener {
                    it.printStackTrace()
                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(Result.Error(DataError.Network.BAD_REQUEST))
                    }
                }
                .addOnCanceledListener {
                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(Result.Error(DataError.Network.CANCELED))
                    }
                }
                .addOnSuccessListener { result ->
                    if (cancellableContinuation.isActive) {

                        val remoteHistories = result.mapNotNull { it.toHistory() }
                        cancellableContinuation.resume(Result.Success(remoteHistories))
                    }
                }



    }


    override suspend fun checkout(
        userId: String,
        cartItems: List<CartItem>
    ): EmptyResult<DataError> =
        withTimeoutOrNull(8.seconds){
            suspendCancellableCoroutine{ cancellableContinuation ->
                val docRef = firebaseStore
                    .collection(DOC_USERS)
                    .document(userId)
                    .collection(DOC_USERS_ORDERS)
                    .document()


                val historyDto = cartItems.toHistoryDto(docRef.id)

                docRef
                    .set(historyDto)
                    .addOnFailureListener {
                        it.printStackTrace()
                        if (cancellableContinuation.isActive) {
                            cancellableContinuation.resume(Result.Error(DataError.Network.BAD_REQUEST))
                        }
                    }
                    .addOnCanceledListener {
                        if (cancellableContinuation.isActive) {
                            cancellableContinuation.resume(Result.Error(DataError.Network.CANCELED))
                        }
                    }
                    .addOnSuccessListener {
                        if (cancellableContinuation.isActive) {
                            cancellableContinuation.resume(Result.Success(Unit))
                        }
                    }
            }
        }?: Result.Error(DataError.Network.NO_INTERNET)

    override suspend fun createUser(user: User): EmptyResult<DataError> =
        suspendCancellableCoroutine { cancellableContinuation ->
            val docRef = firebaseStore
                .collection(DOC_USERS)
                .document(user.id)

            docRef
                .set(user)
                .addOnFailureListener {
                    it.printStackTrace()
                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(Result.Error(DataError.Network.BAD_REQUEST))
                    }
                }
                .addOnCanceledListener {
                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(Result.Error(DataError.Network.CANCELED))
                    }
                }
                .addOnSuccessListener {
                    if (cancellableContinuation.isActive) {
                        cancellableContinuation.resume(Result.Success(Unit))
                    }
                }
    }

    override suspend fun getUserById(id: String): Result<User?, DataError> = suspendCancellableCoroutine{ cancellableContinuation ->
        val docRef = firebaseStore
            .collection(DOC_USERS)
            .document(id)

        docRef
           .get()
           .addOnFailureListener {
               it.printStackTrace()
               if (cancellableContinuation.isActive) {
                   cancellableContinuation.resume(Result.Error(DataError.Network.BAD_REQUEST))
               }
           }
           .addOnCanceledListener {
               if (cancellableContinuation.isActive) {
                   cancellableContinuation.resume(Result.Error(DataError.Network.CANCELED))
               }
           }
           .addOnSuccessListener { result ->
               if (cancellableContinuation.isActive) {
                   val user = result.toObject(User::class.java)
                   cancellableContinuation.resume(Result.Success(user))
               }
           }
    }

    override suspend fun cancelOrder(
        userId: String,
        orderId: String
    ): EmptyResult<DataError> = suspendCancellableCoroutine { cancellableContinuation ->

       firebaseStore
            .collection(DOC_USERS)
            .document(userId)
            .collection(DOC_ORDERS)
            .document(orderId)
            .delete()
            .addOnFailureListener {
                if (cancellableContinuation.isActive){
                    cancellableContinuation.resume(Result.Error(DataError.Network.BAD_REQUEST))
                }
            }
            .addOnCanceledListener {
                if (cancellableContinuation.isActive){
                    cancellableContinuation.resume(Result.Error(DataError.Network.CANCELED))
                }
            }

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