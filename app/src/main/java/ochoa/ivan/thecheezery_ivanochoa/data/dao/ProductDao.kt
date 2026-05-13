package ochoa.ivan.thecheezery_ivanochoa.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM Products ORDER BY productName")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE productType = :type ORDER BY productName")
    fun getProductsByType(type: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE productId = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT COUNT(*) FROM Products")
    suspend fun countProducts(): Int
}
