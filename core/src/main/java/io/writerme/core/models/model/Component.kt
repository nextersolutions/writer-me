package io.writerme.core.models.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.models.enums.ComponentType
import org.mongodb.kbson.ObjectId
import java.util.Date

/**
 * Since Realm doesn't provide polymorphism support
 * all the different component types, such as text,
 * voice records, images, videos, tasks, lists, and
 * links are combined in the following model class
 * as an analog to the 1:N polymorphic relation into
 * the single SQL table
 */
open class Component() : RealmObject {
    @Index
    @PrimaryKey
    var id: String = ObjectId().toHexString()

    var noteId: String = EMPTY

    var content: String = EMPTY

    var isChecked: Boolean = false

    var url: String = EMPTY

    var title: String = EMPTY

    var mediaUrl: String? = null

    private var _time: Long = ZERO.toLong()
    var time: Date
        get() = Date(_time)
        set(value) {
            _time = value.time
        }

    var isImage: Boolean = true

    var changeTime: Long = System.currentTimeMillis()

    private var _type: String = ComponentType.Text.value
    var type: ComponentType
        get() {
            return try {
                ComponentType.valueOf(_type)
            } catch (e: IllegalArgumentException) {
                ComponentType.Text
            }
        }
        set(value) {
            _type = value.value
        }

    constructor(
        note: Note,
        content: String
    ) : this() {
        this.noteId = note.id
        this.content = content
        this.type = ComponentType.Text
    }

    constructor(
        note: Note,
        content: String,
        isChecked: Boolean
    ) : this() {
        this.noteId = note.id
        this.content = content
        this.type = ComponentType.Checkbox
    }

    constructor(
        note: Note,
        date: Date,
        description: String
    ) : this() {
        this.noteId = note.id
        this.time = date
        this.content = description
        this.type = ComponentType.Task
    }

    // voice, media (image, video)
    constructor(
        note: Note,
        url: String,
        type: ComponentType
    ) : this() {
        this.noteId = note.id
        this.url = url
        this.type = type
    }

    constructor(
        note: Note,
        url: String,
        title: String,
        description: String? = null,
        imageUrl: String? = null
    ) : this() {
        this.noteId = note.id
        this.url = url
        this.title = title
        description?.let { this.content = it }
        this.mediaUrl = imageUrl
        this.type = ComponentType.Link
    }

    fun copy(
        id: String = this.id,
        noteId: String = this.noteId,
        content: String = this.content,
        isChecked: Boolean = this.isChecked,
        url: String = this.url,
        title: String = this.title,
        mediaUrl: String? = this.mediaUrl,
        time: Date = this.time,
        isImage: Boolean = this.isImage,
        changeTime: Long = this.changeTime,
        type: ComponentType = this.type
    ): Component {
        return Component().apply {
            this.id = id
            this.noteId = noteId
            this.content = content
            this.isChecked = isChecked
            this.url = url
            this.title = title
            this.mediaUrl = mediaUrl
            this.time = time
            this.isImage = isImage
            this.changeTime = changeTime
            this.type = type
        }
    }

    fun newCopy(
        id: String = ObjectId().toHexString(),
        noteId: String = this.noteId,
        content: String = this.content,
        isChecked: Boolean = this.isChecked,
        url: String = this.url,
        title: String = this.title,
        mediaUrl: String? = this.mediaUrl,
        time: Date = this.time,
        isImage: Boolean = this.isImage,
        changeTime: Long = this.changeTime,
        type: ComponentType = this.type
    ): Component {
        return Component().apply {
            this.id = id
            this.noteId = noteId
            this.content = content
            this.isChecked = isChecked
            this.url = url
            this.title = title
            this.mediaUrl = mediaUrl
            this.time = time
            this.isImage = isImage
            this.changeTime = changeTime
            this.type = type
        }
    }

    companion object {
        fun emptyText(): Component {
            return Component().apply {
                this.type = ComponentType.Text
            }
        }
    }
}
