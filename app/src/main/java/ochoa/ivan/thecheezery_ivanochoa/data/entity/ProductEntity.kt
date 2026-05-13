package ochoa.ivan.thecheezery_ivanochoa.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    val id: Int = 0,

    @ColumnInfo(name = "productName")
    val name: String,

    @ColumnInfo(name = "productPrice")
    val price: Float,

    @ColumnInfo(name = "productImage")
    val image: String? = null,

    @ColumnInfo(name = "productDescription")
    val description: String? = null,

    @ColumnInfo(name = "productType")
    val type: String
)
