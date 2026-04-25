package ochoa.ivan.thecheezery_ivanochoa.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochoa.ivan.thecheezery_ivanochoa.R
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.domain.Product
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Dusty_white
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme

@Composable
fun ProductsScreen(
    innerPadding: PaddingValues,
    type: String,
    products: List<Product>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dusty_white)
            .padding(innerPadding)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(type, textAlign = TextAlign.Center, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        if (products.isEmpty()) {
            Text("No products yet", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products) { product ->
                    ProductRow(product = product)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to menu")
        }
    }
}

@Composable
private fun ProductRow(product: Product) {
    val context = LocalContext.current
    val imageId = context.resources.getIdentifier(product.image.orEmpty(), "drawable", context.packageName)
        .takeIf { it != 0 } ?: R.drawable.ic_launcher_background

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = product.name,
                modifier = Modifier.size(76.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text(product.description.orEmpty(), style = MaterialTheme.typography.bodySmall)
            }
            Text("$${product.price}", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    TheCheezery_IvanOchoaTheme {
        ProductsScreen(
            innerPadding = PaddingValues(20.dp),
            type = DatabaseHelper.TYPE_HOT_DRINKS,
            products = listOf(
                Product(
                    name = "Latte",
                    price = 58f,
                    image = "latte",
                    description = "Smooth coffee.",
                    type = DatabaseHelper.TYPE_HOT_DRINKS
                )
            ),
            onBack = {}
        )
    }
}
