package io.writerme.app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmList
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.data.model.Settings
import io.writerme.app.data.work.ImageLoadingWorker
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.strokeLight
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

fun Bitmap.toFile(
    parentFolder: File
): Uri? {
    return try {
        val file = File("${parentFolder.absolutePath}/image_${System.currentTimeMillis()}.jpg")

        FileOutputStream(file).use { fos ->
            this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }

        file.toUri()
    } catch (e: Exception) {
        null
    }
}

fun WorkManager.scheduleImageLoading(componentId: Long, bookmarkId: Long = -1) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val scheduledNetRequest = OneTimeWorkRequestBuilder<ImageLoadingWorker>()
        .setInputData(
            workDataOf(
                ImageLoadingWorker.IMAGE_COMPONENT_ID to componentId,
                ImageLoadingWorker.BOOKMARK_FOLDER_ID to bookmarkId
            )
        )
        .setConstraints(constraints).build()

    this.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        scheduledNetRequest
    )
}

fun String.toFirstName(): String {
    return if (this.isNotEmpty()) {
        val array = this.split("[\\W\\s]+".toRegex()).toTypedArray()
        return array[0]
    } else this
}

@Composable
fun checkAndRequestPermission(permission: String, onSuccess: () -> Unit, onNotGrantedMessage: Int) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onSuccess()
        } else {
            Toast.makeText(context, onNotGrantedMessage, Toast.LENGTH_LONG).show()
        }
    }

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        onSuccess()
    } else {
        launcher.launch(permission)
    }
}