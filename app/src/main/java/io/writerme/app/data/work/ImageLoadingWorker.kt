package io.writerme.app.data.work

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import io.writerme.app.data.dao.BookmarksFolderDao
import io.writerme.app.data.dao.ComponentDao
import io.writerme.app.data.dao.NoteDao
import io.writerme.app.net.MetaTagScraper
import io.writerme.app.utils.FilesUtil

class ImageLoadingWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WorkerEntryPoint {
        fun componentDao(): ComponentDao
        fun noteDao(): NoteDao
        fun bookmarksFolderDao(): BookmarksFolderDao
    }

    private val componentId = inputData.getLong(IMAGE_COMPONENT_ID, -1)

    override suspend fun doWork(): Result {
        if (componentId < 0) return Result.failure()

        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WorkerEntryPoint::class.java
        )
        val componentDao = entryPoint.componentDao()
        val noteDao = entryPoint.noteDao()
        val bookmarksFolderDao = entryPoint.bookmarksFolderDao()

        val component = componentDao.getById(componentId) ?: return Result.failure()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) return Result.failure()

        val metaTags = MetaTagScraper().scrape(component.url)
        val imageUrl = metaTags.ogImage ?: metaTags.twitterImage
        val title = metaTags.ogTitle ?: metaTags.twitterTitle

        imageUrl?.let { url ->
            val uri = FilesUtil(context).writeImageToFile(url)
            uri?.let { localUri ->
                componentDao.update(
                    component.copy(
                        mediaUrl = localUri,
                        title = title ?: component.title
                    )
                )

                if (component.noteId > 0) {
                    noteDao.updateChangeTime(component.noteId, System.currentTimeMillis())
                } else {
                    val bookmarkId = inputData.getLong(BOOKMARK_FOLDER_ID, -1)
                    if (bookmarkId >= 0) {
                        bookmarksFolderDao.updateChangeTime(bookmarkId, System.currentTimeMillis())
                    }
                }
            }
        }

        return Result.success()
    }

    companion object {
        const val IMAGE_COMPONENT_ID = "ici"
        const val BOOKMARK_FOLDER_ID = "bkm_id"
    }
}
