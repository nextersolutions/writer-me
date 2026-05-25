package io.writerme.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey val id: Long = System.currentTimeMillis()
)
