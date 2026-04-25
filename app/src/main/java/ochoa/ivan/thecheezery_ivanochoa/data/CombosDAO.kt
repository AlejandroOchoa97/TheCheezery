package ochoa.ivan.thecheezery_ivanochoa.data

import android.content.ContentValues
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.CombosEntry
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.ProductsComboEntry
import ochoa.ivan.thecheezery_ivanochoa.domain.Combo

class CombosDAO(private val dbHelper: DatabaseHelper) {
    fun insertCombo(combo: Combo, productIds: List<Int>): Long {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        return try {
            val comboValues = ContentValues().apply {
                put(CombosEntry.COLUMN_NAME, combo.name)
                put(CombosEntry.COLUMN_PRICE, combo.price)
                put(CombosEntry.COLUMN_IMAGE, combo.image)
                put(CombosEntry.COLUMN_DESCRIPTION, combo.description)
            }

            val comboId = db.insert(CombosEntry.TABLE_NAME, null, comboValues)
            if (comboId == -1L) {
                return -1L
            }

            productIds.distinct().forEach { productId ->
                val relationValues = ContentValues().apply {
                    put(ProductsComboEntry.COLUMN_COMBO_ID, comboId)
                    put(ProductsComboEntry.COLUMN_PRODUCT_ID, productId)
                }
                db.insert(ProductsComboEntry.TABLE_NAME, null, relationValues)
            }

            db.setTransactionSuccessful()
            comboId
        } finally {
            db.endTransaction()
        }
    }

    fun getAllCombos(): List<Combo> {
        val db = dbHelper.readableDatabase
        val productDAO = ProductDAO(dbHelper)
        val combos = mutableListOf<Combo>()
        val cursor = db.query(
            CombosEntry.TABLE_NAME,
            arrayOf(
                CombosEntry.COLUMN_ID,
                CombosEntry.COLUMN_NAME,
                CombosEntry.COLUMN_PRICE,
                CombosEntry.COLUMN_IMAGE,
                CombosEntry.COLUMN_DESCRIPTION
            ),
            null,
            null,
            null,
            null,
            CombosEntry.COLUMN_NAME
        )

        cursor.use {
            while (it.moveToNext()) {
                val comboId = it.getInt(it.getColumnIndexOrThrow(CombosEntry.COLUMN_ID))
                val products = getProductIdsForCombo(comboId)
                    .mapNotNull { productId -> productDAO.getProductById(productId) }

                combos.add(
                    Combo(
                        id = comboId,
                        name = it.getString(it.getColumnIndexOrThrow(CombosEntry.COLUMN_NAME)),
                        price = it.getFloat(it.getColumnIndexOrThrow(CombosEntry.COLUMN_PRICE)),
                        image = it.getString(it.getColumnIndexOrThrow(CombosEntry.COLUMN_IMAGE)),
                        description = it.getString(it.getColumnIndexOrThrow(CombosEntry.COLUMN_DESCRIPTION)),
                        products = products
                    )
                )
            }
        }

        return combos
    }

    private fun getProductIdsForCombo(comboId: Int): List<Int> {
        val db = dbHelper.readableDatabase
        val productIds = mutableListOf<Int>()
        val cursor = db.query(
            ProductsComboEntry.TABLE_NAME,
            arrayOf(ProductsComboEntry.COLUMN_PRODUCT_ID),
            "${ProductsComboEntry.COLUMN_COMBO_ID} = ?",
            arrayOf(comboId.toString()),
            null,
            null,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                productIds.add(it.getInt(it.getColumnIndexOrThrow(ProductsComboEntry.COLUMN_PRODUCT_ID)))
            }
        }

        return productIds
    }
}
