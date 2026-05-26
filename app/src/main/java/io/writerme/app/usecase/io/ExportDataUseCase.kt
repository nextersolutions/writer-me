package io.writerme.app.usecase.io

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.data.viewdata.HistoryViewData
import io.writerme.app.data.viewdata.NoteViewData
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

interface ExportDataUseCase {
    suspend operator fun invoke(uri: Uri, exportNotes: Boolean, exportBookmarks: Boolean): Boolean
}

class ExportDataUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteRepository: NoteRepository,
    private val bookmarksRepository: BookmarksRepository
) : ExportDataUseCase {

    override suspend fun invoke(uri: Uri, exportNotes: Boolean, exportBookmarks: Boolean): Boolean {
        return try {
            val root = JSONObject()
            root.put("version", 1)
            root.put("exportTime", System.currentTimeMillis())

            if (exportNotes) {
                val notes = noteRepository.getAllNotesSync()
                val arr = JSONArray()
                notes.forEach { arr.put(it.toJson()) }
                root.put("notes", arr)
            }

            if (exportBookmarks) {
                val folder = bookmarksRepository.exportRootFolder()
                root.put("bookmarks", folder.toJson())
            }

            context.contentResolver.openOutputStream(uri)?.use { stream ->
                stream.writer().use { it.write(root.toString(2)) }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    // ── JSON serializers ──────────────────────────────────────────────────

    private fun ComponentViewData.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("noteId", noteId)
        put("content", content)
        put("isChecked", isChecked)
        put("url", url)
        put("title", title)
        put("mediaUrl", mediaUrl ?: JSONObject.NULL)
        put("time", time.time)
        put("isImage", isImage)
        put("changeTime", changeTime)
        put("type", type.value)
    }

    private fun HistoryViewData.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        val changesArr = JSONArray()
        changes.forEach { changesArr.put(it.toJson()) }
        put("changes", changesArr)
    }

    private fun NoteViewData.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("isImportant", isImportant)
        put("created", created.time)
        put("changeTime", changeTime)

        val tagsArr = JSONArray()
        tags.forEach { tagsArr.put(it) }
        put("tags", tagsArr)

        put("titleHistory", title?.toJson() ?: JSONObject.NULL)
        put("coverHistory", cover?.toJson() ?: JSONObject.NULL)

        val sectionsArr = JSONArray()
        content.forEach { sectionsArr.put(it.toJson()) }
        put("sections", sectionsArr)
    }

    private fun BookmarksFolderViewData.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("name", name)
        put("parentId", parentId ?: JSONObject.NULL)
        put("changeTime", changeTime)

        val foldersArr = JSONArray()
        folders.forEach { foldersArr.put(it.toJson()) }
        put("folders", foldersArr)

        val bookmarksArr = JSONArray()
        bookmarks.forEach { bookmarksArr.put(it.toJson()) }
        put("bookmarks", bookmarksArr)
    }
}
