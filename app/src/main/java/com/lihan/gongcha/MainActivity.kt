package com.lihan.gongcha

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.FirebaseFirestore
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GongchaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(Unit){
                        val db = FirebaseFirestore.getInstance()
                        val collectionRef = db.collection("stores")
                        val jsonString = readJsonFromAssets(this@MainActivity,"store.json")
                        val items: List<StoreJson> = Json.decodeFromString(jsonString)

                        items
                            .chunked(10)
                            .forEach { batchList ->
                                val batch = db.batch()
                                for (item in batchList){
                                    val docRef = collectionRef.document()
                                    batch.set(docRef, item)
                                }
                                batch.commit().await()
                            }
                    }
                }
            }
        }
    }
}

@Serializable
data class TeaJson(
    val name_zh: String,
    val name_en: String,
    val image_url: String,
    val descriptionCh: String,
    val descriptionEn: String,
    val price: Map<String, Int>,
    val type: String
)


@Serializable
data class StoreJson(
    val region: String,
    val name: String,
    val address: String,
    val phone: String,
    val displayHours: String,
    val businessHours: List<BusinessHourJson>
)

@Serializable
data class BusinessHourJson(
    val day: Int,
    val open: String,
    val close: String
)



fun readJsonFromAssets(
    context: Context,
    fileName: String
): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}
