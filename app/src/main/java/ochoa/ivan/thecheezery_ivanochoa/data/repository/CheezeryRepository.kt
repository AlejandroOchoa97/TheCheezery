package ochoa.ivan.thecheezery_ivanochoa.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.data.database.AppDatabase
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductComboEntity
import ochoa.ivan.thecheezery_ivanochoa.data.entity.ProductEntity
import ochoa.ivan.thecheezery_ivanochoa.data.relation.ComboWithProducts
import ochoa.ivan.thecheezery_ivanochoa.domain.Combo
import ochoa.ivan.thecheezery_ivanochoa.domain.Product

class CheezeryRepository(private val database: AppDatabase) {
    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { products -> products.map { it.toDomain() } }
    }

    fun getProductsByType(type: String): Flow<List<Product>> {
        return productDao.getProductsByType(type).map { products -> products.map { it.toDomain() } }
    }

    fun getAllCombos(): Flow<List<Combo>> {
        return comboDao.getAllCombosWithProducts().map { combos -> combos.map { it.toDomain() } }
    }

    suspend fun insertProduct(product: Product): Long {
        return productDao.insertProduct(product.toEntity())
    }

    suspend fun insertCombo(combo: Combo, productIds: List<Int>): Long {
        return database.withTransaction {
            val comboId = comboDao.insertCombo(combo.toEntity())
            if (comboId == -1L) {
                -1L
            } else {
                productComboDao.insertProductCombos(
                    productIds.distinct().map { productId ->
                        ProductComboEntity(comboId = comboId.toInt(), productId = productId)
                    }
                )
                comboId
            }
        }
    }

    suspend fun ensureSeedData() {
        if (productDao.countProducts() > 0) return
        productDao.insertProducts(seedProducts.map { it.toEntity() })
    }

    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            price = price,
            image = image,
            description = description,
            type = type
        )
    }

    private fun ProductEntity.toDomain(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            image = image,
            description = description,
            type = type
        )
    }

    private fun Combo.toEntity(): ComboEntity {
        return ComboEntity(
            id = id,
            name = name,
            price = price,
            image = image,
            description = description
        )
    }

    private fun ComboWithProducts.toDomain(): Combo {
        return Combo(
            id = combo.id,
            name = combo.name,
            price = combo.price,
            image = combo.image,
            description = combo.description,
            products = products.map { it.toDomain() }
        )
    }

    private companion object {
        val seedProducts = listOf(
            Product(name = "Americano", price = 45f, image = "americano", description = "Classic espresso with hot water.", type = DatabaseHelper.TYPE_HOT_DRINKS),
            Product(name = "Capuccino", price = 55f, image = "capuccino", description = "Espresso, steamed milk and foam.", type = DatabaseHelper.TYPE_HOT_DRINKS),
            Product(name = "Latte", price = 58f, image = "latte", description = "Smooth coffee with steamed milk.", type = DatabaseHelper.TYPE_HOT_DRINKS),
            Product(name = "Hot chocolate", price = 50f, image = "hotchocolate", description = "Warm chocolate drink.", type = DatabaseHelper.TYPE_HOT_DRINKS),
            Product(name = "Cold brew", price = 60f, image = "coldbrew", description = "Slow brewed cold coffee.", type = DatabaseHelper.TYPE_COLD_DRINKS),
            Product(name = "Caramel Frap", price = 70f, image = "caramel_frap", description = "Iced caramel blended drink.", type = DatabaseHelper.TYPE_COLD_DRINKS),
            Product(name = "Oreo Milkshake", price = 72f, image = "oreomilkshake", description = "Creamy cookie milkshake.", type = DatabaseHelper.TYPE_COLD_DRINKS),
            Product(name = "Club Sandwich", price = 95f, image = "clubsandwich", description = "Stacked sandwich with fresh fillings.", type = DatabaseHelper.TYPE_SALTIES),
            Product(name = "Nachos", price = 85f, image = "nachos", description = "Crunchy nachos with cheese.", type = DatabaseHelper.TYPE_SALTIES),
            Product(name = "Philly Cheesesteak", price = 110f, image = "phillycheesesteak", description = "Savory steak and cheese sandwich.", type = DatabaseHelper.TYPE_SALTIES),
            Product(name = "Red Velvet Cake", price = 75f, image = "redvelvetcake", description = "Soft cake with cream cheese frosting.", type = DatabaseHelper.TYPE_SWEETS),
            Product(name = "Strawberry Cheesecake", price = 80f, image = "strawberrycheesecake", description = "Cheesecake with strawberry topping.", type = DatabaseHelper.TYPE_SWEETS),
            Product(name = "Chocolate Cupcake", price = 55f, image = "chocolatecupcake", description = "Chocolate cupcake with frosting.", type = DatabaseHelper.TYPE_SWEETS)
        )
    }
}
