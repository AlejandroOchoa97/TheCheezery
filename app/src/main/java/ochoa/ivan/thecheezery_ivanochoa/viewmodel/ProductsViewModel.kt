package ochoa.ivan.thecheezery_ivanochoa.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.data.ProductDAO
import ochoa.ivan.thecheezery_ivanochoa.domain.Product

class ProductsViewModel(databaseHelper: DatabaseHelper, context: Context): ViewModel() {

    val dao = ProductDAO(databaseHelper)
    val contextLocal = context
    fun onSaveProduct(name:String, price:Float, image: String, description: String){
        val product = Product(name= name, price= price, image = image, description= description)
        val idNewProduct= dao.insertProduct(product)

        if(idNewProduct == -1L){
            Toast.makeText(contextLocal, "Hubo un error al guardar!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(contextLocal, "Producto guardado!", Toast.LENGTH_SHORT).show()
        }
    }
    
}