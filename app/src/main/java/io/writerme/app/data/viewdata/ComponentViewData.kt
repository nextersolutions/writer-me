package io.writerme.app.data.viewdata

import io.writerme.app.data.model.ComponentType
import java.util.Date

data class ComponentViewData(
    val id: Long,
    val noteId: Long,
    val content: String,
    val isChecked: Boolean,
    val url: String,
    val title: String,
    val mediaUrl: String?,
    val time: Date,
    val isImage: Boolean,
    val changeTime: Long,
    val type: ComponentType
) {
    companion object {
        fun empty(type: ComponentType = ComponentType.Text, noteId: Long = 0): ComponentViewData =
            ComponentViewData(
                id = System.currentTimeMillis(),
                noteId = noteId,
                content = "",
                isChecked = false,
                url = "",
                title = "",
                mediaUrl = null,
                time = Date(0),
                isImage = true,
                changeTime = System.currentTimeMillis(),
                type = type
            )
    }
}
