package com.lihan.core.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.lihan.core.domain.model.BusinessHour
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRemoteDataSource: RemoteDataSource {

    private val firebaseStore = FirebaseFirestore.getInstance()

    companion object {
        private const val DOC_TEAS = "teas"
        private const val DOC_STORES = "stores"
        private const val DOC_VERSIONS = "versions"
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