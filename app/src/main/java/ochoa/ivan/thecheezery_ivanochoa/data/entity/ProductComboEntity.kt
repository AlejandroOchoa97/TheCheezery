package ochoa.ivan.thecheezery_ivanochoa.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ProductsCombo",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ComboEntity::class,
            parentColumns = ["comboId"],
            childColumns = ["comboId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["comboId"]),
        Index(value = ["productId", "comboId"], unique = true)
    ]
)
data class ProductComboEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productsComboId")
    val id: Int = 0,

    @ColumnInfo(name = "comboId")
    val comboId: Int,

    @ColumnInfo(name = "productId")
    val productId: Int
)
