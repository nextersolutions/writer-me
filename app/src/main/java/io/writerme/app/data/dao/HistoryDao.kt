package io.writerme.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.HistoryComponentCrossRef
import io.writerme.app.data.model.HistoryEntity

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryEntity)

    @Query("SELECT * FROM histories WHERE id = :id")
    suspend fun getById(id: Long): HistoryEntity?

    @Query(
        """SELECT c.* FROM components c
           INNER JOIN history_components hc ON c.id = hc.componentId
           WHERE hc.historyId = :historyId
           ORDER BY hc.position ASC"""
    )
    suspend fun getComponentsSorted(historyId: Long): List<ComponentEntity>

    @Query("SELECT COUNT(*) FROM history_components WHERE historyId = :historyId")
    suspend fun getComponentCount(historyId: Long): Int

    @Query("SELECT MAX(position) FROM history_components WHERE historyId = :historyId")
    suspend fun getMaxPosition(historyId: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: HistoryComponentCrossRef)

    @Query("DELETE FROM history_components WHERE historyId = :historyId AND componentId = :componentId")
    suspend fun deleteCrossRef(historyId: Long, componentId: Long)

    @Query("DELETE FROM histories WHERE id = :id")
    suspend fun deleteById(id: Long)
}
