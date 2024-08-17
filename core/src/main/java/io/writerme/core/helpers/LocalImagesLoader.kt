package io.writerme.core.helpers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Size
import io.writerme.core.extensions.toFile
import java.io.File
import javax.inject.Inject

class LocalImagesLoader @Inject constructor(
    private val context: Context
) {
    suspend fun writeImageToFile(url: Uri): File? {
        val config = Bitmap.Config.RGB_565

        val imageLoader = ImageLoader
            .Builder(context)
            .allowRgb565(true)
            .bitmapConfig(config)
            .build()

        val request = ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(false)
            .build()

        val drawable = imageLoader.execute(request).drawable

        return try {
            if (drawable != null) {
                val bitmap = drawable.toBitmap(config = config)
                bitmap.toFile(context.filesDir)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
