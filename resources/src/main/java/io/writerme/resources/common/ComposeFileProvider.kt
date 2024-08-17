package io.writerme.resources.common

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import io.writerme.core.common.GlobalConstants
import io.writerme.resources.R
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.provider_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val file = File.createTempFile(
                "yoni",
                ".png",
                context.externalCacheDir
            )
            return getUriForFile(
                context,
                GlobalConstants.contentProviderAuthority,
                file
            )
        }
    }
}
