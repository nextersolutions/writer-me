package io.writerme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "components")
data class ComponentEntity(
    @PrimaryKey val id: Long = System.currentTimeMillis(),
    val noteId: Long = 0,
    val content: String = "",
    val isChecked: Boolean = false,
    val url: String = "",
    val title: String = "",
    val mediaUrl: String? = null,
    val time: Long = 0,
    val isImage: Boolean = true,
    val changeTime: Long = System.currentTimeMillis(),
    val type: String = ComponentType.Text.value
)

enum class ComponentType(val value: String) {
    Text("Text"),
    Checkbox("Checkbox"),
    Voice("Voice"),
    Task("Task"),
    Link("Link"),
    Video("Video"),
    Image("Image");

    companion object {
        fun fromValue(value: String): ComponentType =
            entries.firstOrNull { it.value == value } ?: Text
    }
}
