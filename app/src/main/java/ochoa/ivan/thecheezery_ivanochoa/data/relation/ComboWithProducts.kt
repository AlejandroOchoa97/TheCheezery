package ochoa.ivan.thecheezery_ivanochoa.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductEntity

data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "comboId",
        entityColumn = "productId",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "comboId",
            entityColumn = "productId"
        )
    )
    val products: List<ProductEntity>
)
