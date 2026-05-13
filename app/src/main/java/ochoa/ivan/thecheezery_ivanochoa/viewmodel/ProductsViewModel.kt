package ochoa.ivan.thecheezery_ivanochoa.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ochoa.ivan.thecheezery_ivanochoa.data.repository.CheezeryRepository
import ochoa.ivan.thecheezery_ivanochoa.domain.Combo
import ochoa.ivan.thecheezery_ivanochoa.domain.Product

class ProductsViewModel(
    private val repository: CheezeryRepository,
    private val contextLocal: Context
) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var combos by mutableStateOf<List<Combo>>(emptyList())
        private set

    private var productsJob: Job? = null
    private var combosJob: Job? = null
    private var currentProductType: String? = null

    init {
        viewModelScope.launch {
            repository.ensureSeedData()
        }
        loadProducts()
        loadCombos()
    }

    fun loadProducts(type: String? = null) {
        currentProductType = type
        productsJob?.cancel()
        productsJob = viewModelScope.launch {
            val productsFlow = if (type == null) {
                repository.getAllProducts()
            } else {
                repository.getProductsByType(type)
            }
            productsFlow.collect { loadedProducts ->
                products = loadedProducts
            }
        }
    }

    fun loadCombos() {
        combosJob?.cancel()
        combosJob = viewModelScope.launch {
            repository.getAllCombos().collect { loadedCombos ->
                combos = loadedCombos
            }
        }
    }

    fun onSaveProduct(name: String, price: Float, image: String, description: String, type: String) {
        viewModelScope.launch {
            val product = Product(name = name, price = price, image = image, description = description, type = type)
            val idNewProduct = repository.insertProduct(product)

            if (idNewProduct == -1L) {
                Toast.makeText(contextLocal, "Hubo un error al guardar!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(contextLocal, "Producto guardado!", Toast.LENGTH_SHORT).show()
                if (currentProductType != type) {
                    loadProducts(type)
                }
            }
        }
    }

    fun onSaveCombo(name: String, price: Float, image: String, description: String, productIds: List<Int>) {
        viewModelScope.launch {
            val combo = Combo(name = name, price = price, image = image, description = description)
            val idNewCombo = repository.insertCombo(combo, productIds)

            if (idNewCombo == -1L) {
                Toast.makeText(contextLocal, "Hubo un error al guardar el combo!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(contextLocal, "Combo guardado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
