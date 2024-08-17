package io.writerme.core.extensions

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

fun Bitmap.toFile(
    parentFolder: File
): File? {
    return try {
        val file = File("${parentFolder.absolutePath}/image_${System.currentTimeMillis()}.jpg")

        FileOutputStream(file).use { fos ->
            this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }

        file
    } catch (e: Exception) {
        null
    }
}
