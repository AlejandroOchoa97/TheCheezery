package ochoa.ivan.thecheezery_ivanochoa.data

import android.content.ContentValues
import ochoa.ivan.thecheezery_ivanochoa.domain.Product
import ochoa.ivan.thecheezery_ivanochoa.data.CheezeryContract.ProductsEntry

class ProductDAO(private val dbHelper: DatabaseHelper) {
    fun insertProduct(product: Product): Long{
        val db= dbHelper.writableDatabase
        val values = ContentValues().apply{
            put(ProductsEntry.COLUMN_NAME, product.name)
            put(ProductsEntry.COLUMN_PRICE, product.id)
            put(ProductsEntry.COLUMN_DESCRIPTION, product.description)
            put(ProductsEntry.COLUMN_IMAGE, product.image)
        }

        return db.insert(ProductsEntry.TABLE_NAME, null, values)

    }

    fun getAllProducts():List<Product>{
        val db= dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(ProductsEntry.COLUMN_ID,
                ProductsEntry.TABLE_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE),
            null, null, null, null, null
        )

        val product = mutableListOf<Product>()

        with(cursor){
            while (moveToNext()){
                val id = getInt (getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString (getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat (getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = getString (getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                product.add(Product(id, name, price, image, description))
            }
        }
        cursor.close()
        return product
    }

    fun getProductById(productId: Int): Product?{
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
        arrayOf(ProductsEntry.COLUMN_ID,
        ProductsEntry.TABLE_NAME,
        ProductsEntry.COLUMN_PRICE,
        ProductsEntry.COLUMN_DESCRIPTION,
        ProductsEntry.COLUMN_IMAGE
        ),
        "${ProductsEntry.COLUMN_ID} = ?",
        arrayOf(productId.toString()),
        null,
        null, null
        )

        val product : Product? = cursor.use{
            if(it.moveToFirst()){
                val id = it.getInt(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = it.getFloat (it.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = it.getString (it.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                Product(id, name, price, image, description)
            }else{
                null
            }
        }

        return product
    }
}