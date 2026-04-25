package ochoa.ivan.thecheezery_ivanochoa.domain

data class Combo(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val image: String? = null,
    val description: String? = null,
    val products: List<Product> = emptyList()
)
