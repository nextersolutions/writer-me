package io.writerme.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.writerme.app.data.model.ComponentEntity

@Dao
interface ComponentDao {

    @Query("SELECT * FROM components WHERE id = :id")
    suspend fun getById(id: Long): ComponentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(component: ComponentEntity)

    @Update
    suspend fun update(component: ComponentEntity)

    @Query("DELETE FROM components WHERE id = :id")
    suspend fun deleteById(id: Long)
}
