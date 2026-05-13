package ochoa.ivan.thecheezery_ivanochoa.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Combos")
data class ComboEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comboId")
    val id: Int = 0,

    @ColumnInfo(name = "comboName")
    val name: String,

    @ColumnInfo(name = "comboPrice")
    val price: Float,

    @ColumnInfo(name = "comboImage")
    val image: String? = null,

    @ColumnInfo(name = "comboDescription")
    val description: String? = null
)
