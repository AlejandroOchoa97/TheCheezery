package ochoa.ivan.thecheezery_ivanochoa.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ochoa.ivan.thecheezery_ivanochoa.data.CombosDAO
import ochoa.ivan.thecheezery_ivanochoa.data.ProductDAO
import ochoa.ivan.thecheezery_ivanochoa.domain.Combo
import ochoa.ivan.thecheezery_ivanochoa.domain.Product

class ProductsViewModel(
    private val productDAO: ProductDAO,
    private val combosDAO: CombosDAO,
    private val contextLocal: Context
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var combos by mutableStateOf<List<Combo>>(emptyList())
        private set

    init {
        loadProducts()
        loadCombos()
    }

    fun loadProducts(type: String? = null) {
        products = if (type == null) {
            productDAO.getAllProducts()
        } else {
            productDAO.getProductsByType(type)
        }
    }

    fun loadCombos() {
        combos = combosDAO.getAllCombos()
    }

    fun onSaveProduct(name:String, price:Float, image: String, description: String, type: String){
        val product = Product(name= name, price= price, image = image, description= description, type = type)
        val idNewProduct= productDAO.insertProduct(product)

        if(idNewProduct == -1L){
            Toast.makeText(contextLocal, "Hubo un error al guardar!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(contextLocal, "Producto guardado!", Toast.LENGTH_SHORT).show()
            loadProducts(type)
        }
    }

    fun onSaveCombo(name: String, price: Float, image: String, description: String, productIds: List<Int>) {
        val combo = Combo(name = name, price = price, image = image, description = description)
        val idNewCombo = combosDAO.insertCombo(combo, productIds)

        if (idNewCombo == -1L) {
            Toast.makeText(contextLocal, "Hubo un error al guardar el combo!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(contextLocal, "Combo guardado!", Toast.LENGTH_SHORT).show()
            loadCombos()
        }
    }
    
}
