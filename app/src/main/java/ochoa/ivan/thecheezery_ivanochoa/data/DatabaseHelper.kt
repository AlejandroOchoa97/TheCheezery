package ochoa.ivan.thecheezery_ivanochoa.data

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.CombosEntry
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.ProductsComboEntry
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.ProductsEntry

class DatabaseHelper(context : Context) :
    SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME= "cheezery.db"
        private const val DATABASE_VERSION = 2

        const val TYPE_HOT_DRINKS = "Hot drinks"
        const val TYPE_COLD_DRINKS = "Cold drinks"
        const val TYPE_SALTIES = "Salties"
        const val TYPE_SWEETS = "Sweets"

        val PRODUCT_TYPES = listOf(
            TYPE_HOT_DRINKS,
            TYPE_COLD_DRINKS,
            TYPE_SALTIES,
            TYPE_SWEETS
        )
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")

        db.execSQL(
            """CREATE TABLE ${ProductsEntry.TABLE_NAME} ( 
             ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
             ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
             ${ProductsEntry.COLUMN_IMAGE} TEXT,
             ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
             ${ProductsEntry.COLUMN_DESCRIPTION} TEXT,
             ${ProductsEntry.COLUMN_TYPE} TEXT NOT NULL
             )
             """.trimIndent()
        )

        db.execSQL(
            """CREATE TABLE ${CombosEntry.TABLE_NAME} (
             ${CombosEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
             ${CombosEntry.COLUMN_NAME} TEXT NOT NULL,
             ${CombosEntry.COLUMN_IMAGE} TEXT,
             ${CombosEntry.COLUMN_PRICE} REAL NOT NULL,
             ${CombosEntry.COLUMN_DESCRIPTION} TEXT
             )
             """.trimIndent()
        )

        db.execSQL(
            """CREATE TABLE ${ProductsComboEntry.TABLE_NAME} (
             ${ProductsComboEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
             ${ProductsComboEntry.COLUMN_COMBO_ID} INTEGER NOT NULL,
             ${ProductsComboEntry.COLUMN_PRODUCT_ID} INTEGER NOT NULL,
             UNIQUE (${ProductsComboEntry.COLUMN_COMBO_ID}, ${ProductsComboEntry.COLUMN_PRODUCT_ID}),
             FOREIGN KEY (${ProductsComboEntry.COLUMN_COMBO_ID}) REFERENCES ${CombosEntry.TABLE_NAME}(${CombosEntry.COLUMN_ID}) ON DELETE CASCADE,
             FOREIGN KEY (${ProductsComboEntry.COLUMN_PRODUCT_ID}) REFERENCES ${ProductsEntry.TABLE_NAME}(${ProductsEntry.COLUMN_ID}) ON DELETE CASCADE
             )
             """.trimIndent()
        )

        insertInitialProducts(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductsComboEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CombosEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys = ON")
    }

    private fun insertInitialProducts(db: SQLiteDatabase) {
        val products = listOf(
            SeedProduct("Americano", 45f, "americano", "Classic espresso with hot water.", TYPE_HOT_DRINKS),
            SeedProduct("Capuccino", 55f, "capuccino", "Espresso, steamed milk and foam.", TYPE_HOT_DRINKS),
            SeedProduct("Latte", 58f, "latte", "Smooth coffee with steamed milk.", TYPE_HOT_DRINKS),
            SeedProduct("Hot chocolate", 50f, "hotchocolate", "Warm chocolate drink.", TYPE_HOT_DRINKS),
            SeedProduct("Cold brew", 60f, "coldbrew", "Slow brewed cold coffee.", TYPE_COLD_DRINKS),
            SeedProduct("Caramel Frap", 70f, "caramel_frap", "Iced caramel blended drink.", TYPE_COLD_DRINKS),
            SeedProduct("Oreo Milkshake", 72f, "oreomilkshake", "Creamy cookie milkshake.", TYPE_COLD_DRINKS),
            SeedProduct("Club Sandwich", 95f, "clubsandwich", "Stacked sandwich with fresh fillings.", TYPE_SALTIES),
            SeedProduct("Nachos", 85f, "nachos", "Crunchy nachos with cheese.", TYPE_SALTIES),
            SeedProduct("Philly Cheesesteak", 110f, "phillycheesesteak", "Savory steak and cheese sandwich.", TYPE_SALTIES),
            SeedProduct("Red Velvet Cake", 75f, "redvelvetcake", "Soft cake with cream cheese frosting.", TYPE_SWEETS),
            SeedProduct("Strawberry Cheesecake", 80f, "strawberrycheesecake", "Cheesecake with strawberry topping.", TYPE_SWEETS),
            SeedProduct("Chocolate Cupcake", 55f, "chocolatecupcake", "Chocolate cupcake with frosting.", TYPE_SWEETS)
        )

        products.forEach { product ->
            val values = ContentValues().apply {
                put(ProductsEntry.COLUMN_NAME, product.name)
                put(ProductsEntry.COLUMN_PRICE, product.price)
                put(ProductsEntry.COLUMN_IMAGE, product.image)
                put(ProductsEntry.COLUMN_DESCRIPTION, product.description)
                put(ProductsEntry.COLUMN_TYPE, product.type)
            }
            db.insert(ProductsEntry.TABLE_NAME, null, values)
        }
    }

    private data class SeedProduct(
        val name: String,
        val price: Float,
        val image: String,
        val description: String,
        val type: String
    )
}
