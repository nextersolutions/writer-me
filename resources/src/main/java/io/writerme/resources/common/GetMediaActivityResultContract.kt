package io.writerme.resources.common

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import io.writerme.core.common.FormatUtils.MimeType

class GetMediaActivityResultContract : ActivityResultContracts.GetContent() {
    override fun createIntent(context: Context, input: String): Intent {
        return super.createIntent(context, input).apply {
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(MimeType.image))
        }
    }
}
