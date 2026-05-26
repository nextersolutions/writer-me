package io.writerme.app.usecase.io

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.viewdata.BookmarksFolderViewData
import io.writerme.app.data.viewdata.ComponentViewData
import io.writerme.app.data.viewdata.HistoryViewData
import io.writerme.app.data.viewdata.NoteViewData
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date
import javax.inject.Inject

interface ImportDataUseCase {
    suspend operator fun invoke(uri: Uri): Boolean
}

class ImportDataUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteRepository: NoteRepository,
    private val bookmarksRepository: BookmarksRepository
) : ImportDataUseCase {

    override suspend fun invoke(uri: Uri): Boolean {
        return try {
            val content = context.contentResolver.openInputStream(uri)
                ?.bufferedReader()
                ?.use { it.readText() }
                ?: return false

            val root = JSONObject(content)

            if (root.has("notes")) {
                val arr = root.getJSONArray("notes")
                val notes = (0 until arr.length()).map { arr.getJSONObject(it).toNoteViewData() }
                noteRepository.importNotes(notes)
            }

            if (root.has("bookmarks")) {
                val folderJson = root.getJSONObject("bookmarks")
                val folder = folderJson.toBookmarksFolderViewData()
                bookmarksRepository.importFolder(folder, null)
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    // ── JSON deserializers ────────────────────────────────────────────────

    private fun JSONObject.toComponentViewData(): ComponentViewData = ComponentViewData(
        id = getLong("id"),
        noteId = getLong("noteId"),
        content = optString("content", ""),
        isChecked = optBoolean("isChecked", false),
        url = optString("url", ""),
        title = optString("title", ""),
        mediaUrl = optString("mediaUrl", "").takeIf { it.isNotEmpty() },
        time = Date(optLong("time", 0L)),
        isImage = optBoolean("isImage", false),
        changeTime = optLong("changeTime", System.currentTimeMillis()),
        type = ComponentType.fromValue(optString("type", ComponentType.Text.value))
    )

    private fun JSONArray.toComponentList(): List<ComponentViewData> =
        (0 until length()).map { getJSONObject(it).toComponentViewData() }

    private fun JSONObject.toHistoryViewData(): HistoryViewData = HistoryViewData(
        id = getLong("id"),
        changes = getJSONArray("changes").toComponentList()
    )

    private fun JSONObject.toNoteViewData(): NoteViewData {
        val tagsArr = optJSONArray("tags") ?: JSONArray()
        val tags = (0 until tagsArr.length()).map { tagsArr.getString(it) }

        val sectionsArr = optJSONArray("sections") ?: JSONArray()
        val sections = (0 until sectionsArr.length()).map {
            sectionsArr.getJSONObject(it).toHistoryViewData()
        }

        return NoteViewData(
            id = getLong("id"),
            isImportant = optBoolean("isImportant", false),
            created = Date(optLong("created", System.currentTimeMillis())),
            changeTime = optLong("changeTime", System.currentTimeMillis()),
            tags = tags,
            title = optJSONObject("titleHistory")?.toHistoryViewData(),
            cover = optJSONObject("coverHistory")?.toHistoryViewData(),
            content = sections
        )
    }

    private fun JSONObject.toBookmarksFolderViewData(): BookmarksFolderViewData {
        val foldersArr = optJSONArray("folders") ?: JSONArray()
        val folders = (0 until foldersArr.length()).map {
            foldersArr.getJSONObject(it).toBookmarksFolderViewData()
        }

        val bookmarksArr = optJSONArray("bookmarks") ?: JSONArray()
        val bookmarks = (0 until bookmarksArr.length()).map {
            bookmarksArr.getJSONObject(it).toComponentViewData()
        }

        return BookmarksFolderViewData(
            id = getLong("id"),
            name = optString("name", ""),
            path = "",
            folders = folders,
            bookmarks = bookmarks,
            parentId = if (isNull("parentId")) null else optLong("parentId"),
            changeTime = optLong("changeTime", System.currentTimeMillis())
        )
    }
}
