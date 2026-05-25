package io.writerme.app.data.model

import androidx.room.Entity

@Entity(
    tableName = "history_components",
    primaryKeys = ["historyId", "componentId"]
)
data class HistoryComponentCrossRef(
    val historyId: Long,
    val componentId: Long,
    val position: Int
)
