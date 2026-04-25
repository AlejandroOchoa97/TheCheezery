package ochoa.ivan.thecheezery_ivanochoa.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.domain.Combo
import ochoa.ivan.thecheezery_ivanochoa.domain.Product
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Dusty_white
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme

@Composable
fun AddComboScreen(
    innerPadding: PaddingValues,
    products: List<Product>,
    combos: List<Combo>,
    onSaveCombo: (name: String, price: Float, image: String, description: String, productIds: List<Int>) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var priceField by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("grupo_1") }
    var description by remember { mutableStateOf("") }
    var selectedProductIds by remember { mutableStateOf(setOf<Int>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dusty_white)
            .padding(innerPadding)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Add combo", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Combo name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = priceField,
            onValueChange = { priceField = it },
            label = { Text("Combo price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = image,
            onValueChange = { image = it },
            label = { Text("Image resource name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Products", fontWeight = FontWeight.Bold)
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                val selected = product.id in selectedProductIds
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selected,
                            onCheckedChange = { checked ->
                                selectedProductIds = if (checked) {
                                    selectedProductIds + product.id
                                } else {
                                    selectedProductIds - product.id
                                }
                            }
                        )
                        Column {
                            Text(product.name, fontWeight = FontWeight.Bold)
                            Text(product.type, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            if (combos.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Text("Saved combos", fontWeight = FontWeight.Bold)
                }
                items(combos) { combo ->
                    Text("${combo.name} - $${combo.price}")
                }
            }
        }

        Button(
            onClick = {
                onSaveCombo(name, priceField.toFloatOrNull() ?: 0f, image, description, selectedProductIds.toList())
                name = ""
                priceField = ""
                image = "grupo_1"
                description = ""
                selectedProductIds = emptySet()
            },
            enabled = name.isNotBlank() && selectedProductIds.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save combo")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to menu")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddComboScreenPreview() {
    val previewProducts = listOf(
        Product(
            id = 1,
            name = "Latte",
            price = 58f,
            image = "latte",
            description = "Smooth coffee with steamed milk.",
            type = DatabaseHelper.TYPE_HOT_DRINKS
        ),
        Product(
            id = 2,
            name = "Club Sandwich",
            price = 95f,
            image = "clubsandwich",
            description = "Stacked sandwich with fresh fillings.",
            type = DatabaseHelper.TYPE_SALTIES
        )
    )

    val previewCombos = listOf(
        Combo(
            id = 1,
            name = "Lunch combo",
            price = 140f,
            image = "grupo_1",
            description = "Coffee plus sandwich.",
            products = previewProducts
        )
    )

    TheCheezery_IvanOchoaTheme {
        AddComboScreen(
            innerPadding = PaddingValues(),
            products = previewProducts,
            combos = previewCombos,
            onSaveCombo = { _, _, _, _, _ -> },
            onBack = {}
        )
    }
}
