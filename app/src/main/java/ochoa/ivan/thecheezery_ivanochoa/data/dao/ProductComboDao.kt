package ochoa.ivan.thecheezery_ivanochoa.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductComboEntity

@Dao
interface ProductComboDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductCombos(productCombos: List<ProductComboEntity>)

    @Query("DELETE FROM ProductsCombo WHERE comboId = :comboId")
    suspend fun deleteProductsForCombo(comboId: Int)
}
