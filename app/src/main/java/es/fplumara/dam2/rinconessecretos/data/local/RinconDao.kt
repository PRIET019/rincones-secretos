package es.fplumara.dam2.rinconessecretos.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RinconDao {



    @Query("SELECT * FROM rincones")
    fun getAll(): Flow<List<RinconEntity>>

    @Query("SELECT * FROM rincones WHERE id = :id")
    suspend fun getById(id: Long): RinconEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rincon: RinconEntity): Long

    @Delete
    suspend fun delete(rincon: RinconEntity)

    @Query("DELETE FROM rincones WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(rincon: RinconEntity)

}
