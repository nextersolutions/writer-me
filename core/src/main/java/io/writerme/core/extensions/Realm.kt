package io.writerme.core.extensions

import android.util.Log
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.GlobalConstants.HistoryDefaults
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.model.Component
import io.writerme.core.models.model.History
import io.writerme.core.models.model.Note
import java.io.File

fun <T> List<T>.toRealmList(): RealmList<T> {
    val result = realmListOf<T>()
    result.addAll(this)
    return result
}

fun <T> RealmList<T>.getLast(): T? {
    return if (this.isNotEmpty()) {
        if (this.size > VALUE_1) {
            this[size - VALUE_1]
        } else {
            this.first()
        }
    } else {
        null
    }
}

/**
 * IMPORTANT: objects are not deleted from Realm but should be
 */
fun RealmList<Component>.push(t: Component): Component? {
    val number = when (t.type) {
        ComponentType.Text -> HistoryDefaults.TEXT_CHANGES_HISTORY
        ComponentType.Checkbox -> HistoryDefaults.TEXT_CHANGES_HISTORY
        ComponentType.Link -> HistoryDefaults.LINK_CHANGES_HISTORY
        ComponentType.Image, ComponentType.Video -> HistoryDefaults.MEDIA_CHANGES_HISTORY
        ComponentType.Voice -> HistoryDefaults.VOICE_CHANGES_HISTORY
        ComponentType.Task -> HistoryDefaults.TASK_CHANGES_HISTORY
    }

    val deleted = if (this.size >= number) {
        val element = this.first()
        this.removeFirst()
        element
    } else {
        null
    }

    this.add(t)

    return deleted
}

fun MutableRealm.deleteComponent(component: Component) {
    when (component.type) {
        ComponentType.Voice,
        ComponentType.Link,
        ComponentType.Video, ComponentType.Image -> {
            component.mediaUrl?.let { url ->
                if (url.isNotEmpty()) {
                    try {
                        val file = File(url)

                        if (file.exists()) file.delete()
                    } catch (e: Exception) {
                        Log.e("deleteComponent", "Component deletion is failed", e)
                    }
                }
            }
        }

        else -> {}
    }
    delete(component)
}

fun MutableRealm.deleteHistory(h: History?) {
    h?.let { history ->
        if (history.changes.isNotEmpty()) {
            while (history.changes.isNotEmpty()) {
                val component = history.changes.first()
                deleteComponent(component)
            }
        }
        delete(history)
    }
}

fun MutableRealm.deleteNote(note: Note) {
    deleteHistory(note.title)
    deleteHistory(note.cover)

    if (note.content.isNotEmpty()) {
        while (note.content.isNotEmpty()) {
            val component = note.content.first()
            deleteHistory(component)
        }
    }

    delete(note)
}

fun MutableRealm.deleteBookmarkFolder(folder: BookmarksFolder) {
    while (folder.folders.isNotEmpty()) {
        this.deleteBookmarkFolder(folder.folders.first())
    }

    while (folder.bookmarks.isNotEmpty()) {
        this.deleteComponent(folder.bookmarks.first())
    }

    delete(folder)
}
