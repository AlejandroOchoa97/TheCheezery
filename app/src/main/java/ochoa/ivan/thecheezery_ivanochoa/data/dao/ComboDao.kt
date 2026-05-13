package ochoa.ivan.thecheezery_ivanochoa.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.relation.ComboWithProducts

@Dao
interface ComboDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombo(combo: ComboEntity): Long

    @Update
    suspend fun updateCombo(combo: ComboEntity)

    @Delete
    suspend fun deleteCombo(combo: ComboEntity)

    @Transaction
    @Query("SELECT * FROM Combos ORDER BY comboName")
    fun getAllCombosWithProducts(): Flow<List<ComboWithProducts>>

    @Transaction
    @Query("SELECT * FROM Combos WHERE comboId = :comboId")
    suspend fun getComboById(comboId: Int): ComboWithProducts?
}
