package io.writerme.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowRgb565
import coil3.request.bitmapConfig
import coil3.request.crossfade
import coil3.size.Size
import coil3.toBitmap

class FilesUtil(private val context: Context) {

    suspend fun writeImageToFile(url: String): String? {
        val config = Bitmap.Config.RGB_565

        val imageLoader = ImageLoader.Builder(context)
            .allowRgb565(true)
            .bitmapConfig(config)
            .build()

        val request = ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(false)
            .build()

        val result = imageLoader.execute(request)

        return if (result is SuccessResult) {
            val bitmap = result.image.toBitmap()
            Log.d("FilesUtil", "width: ${bitmap.width}, height: ${bitmap.height}")
            bitmap.toFile(context.filesDir)?.path
        } else {
            Log.d("FilesUtil", "bitmap is null")
            null
        }
    }
}
