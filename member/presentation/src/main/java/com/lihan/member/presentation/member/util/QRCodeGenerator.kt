package com.lihan.member.presentation.member.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.CancellationException

class QRCodeGenerator {

    companion object{
        private const val SIZE = 512
    }

    suspend fun generate(
        content: String
    ): Bitmap? = withContext(Dispatchers.Default) {
        if (content.isBlank()) return@withContext null
        try {
            val hints = mapOf(
                EncodeHintType.CHARACTER_SET to "UTF-8",
                EncodeHintType.MARGIN to 1
            )

            val bitMatrix = QRCodeWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                SIZE,
                SIZE,
                hints
            )

            val pixels = IntArray(SIZE * SIZE)
            for (y in 0 until SIZE) {
                val offset = y * SIZE
                for (x in 0 until SIZE) {
                    // 黑色區塊與白色背景，若想做透明底可將 Color.WHITE 改為 Color.TRANSPARENT
                    pixels[offset + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                }
            }

            ensureActive()

            createBitmap(SIZE, SIZE).apply {
                setPixels(pixels, 0, SIZE, 0, 0, SIZE, SIZE)
            }
        }catch (e: CancellationException){
            throw e
        }
        catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

}