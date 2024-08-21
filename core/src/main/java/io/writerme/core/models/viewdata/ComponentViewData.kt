package io.writerme.core.models.viewdata

import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.models.enums.ComponentType
import java.util.Date

data class ComponentViewData(
    val id: String = EMPTY,
    val noteId: String = EMPTY,
    val content: String = EMPTY,
    val isChecked: Boolean = false,
    val url: String = EMPTY,
    val title: String = EMPTY,
    val mediaUrl: String? = null,
    val time: Date = Date(),
    val isImage: Boolean = true,
    val changeTime: Long = System.currentTimeMillis(),
    val type: ComponentType = ComponentType.Text
)
