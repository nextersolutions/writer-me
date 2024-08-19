package io.writerme.database.extensions

import io.writerme.database.model.BookmarksFolder
import io.writerme.database.model.Component
import io.writerme.database.model.History
import io.writerme.database.model.Note

fun <T> RealmList<T>.getLast(): T? {
    return if (this.size > 0) {
        if (this.size > 1) {
            this[size-1]
        } else this[0]
    } else null
}


/**
 * IMPORTANT: objects are not deleted from Realm but should be
 */
fun RealmList<Component>.push(t: Component): Component? {
    val number = when (t.type) {
        ComponentType.Text -> Const.TEXT_CHANGES_HISTORY
        ComponentType.Checkbox -> Const.TEXT_CHANGES_HISTORY
        ComponentType.Link -> Const.LINK_CHANGES_HISTORY
        ComponentType.Image, ComponentType.Video -> Const.MEDIA_CHANGES_HISTORY
        ComponentType.Voice -> Const.VOICE_CHANGES_HISTORY
        ComponentType.Task -> Const.TASK_CHANGES_HISTORY
    }

    val deleted = if (this.size >= number) {
        val element = this[0]
        this.removeAt(0)
        element
    } else null

    this.add(t)

    return deleted
}


fun RealmConfiguration.Companion.default(): RealmConfiguration {
    return RealmConfiguration.Builder(
        schema = setOf(BookmarksFolder::class, Component::class, History::class, Note::class, Settings::class)
    )
        .name(Const.DB_NAME)
        .schemaVersion(Const.DB_SCHEMA_VERSION)
        .build()
}

fun Realm.Companion.getDefaultInstance(): Realm {
    return open(RealmConfiguration.default())
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
                val component = history.changes[0]
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
            val component = note.content[0]
            deleteHistory(component)
        }
    }

    delete(note)
}

fun MutableRealm.deleteBookmarkFolder(folder: BookmarksFolder) {
    while (folder.folders.isNotEmpty()) {
        this.deleteBookmarkFolder(folder.folders[0])
    }

    while (folder.bookmarks.isNotEmpty()) {
        this.deleteComponent(folder.bookmarks[0])
    }

    delete(folder)
}