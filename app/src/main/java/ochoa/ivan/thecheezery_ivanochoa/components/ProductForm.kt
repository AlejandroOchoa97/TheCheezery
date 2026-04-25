package ochoa.ivan.thecheezery_ivanochoa.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ochoa.ivan.thecheezery_ivanochoa.R
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper

@Composable
fun ProductForm(
    onSaveProduct: (name: String, price: Float, image: String, description: String, type: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var priceField by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(DatabaseHelper.TYPE_HOT_DRINKS) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text("Add product")
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = priceField,
            onValueChange = { priceField = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = { Image(painter = painterResource(R.drawable.dolar), contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = image,
            onValueChange = { image = it },
            label = { Text("Image resource name") },
            placeholder = { Text("americano") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(14.dp))
        ProductTypeSelector(selectedType = type, onTypeSelected = { type = it })

        Spacer(Modifier.height(18.dp))
        Button(
            onClick = {
                onSaveProduct(name, priceField.toFloatOrNull() ?: 0f, image, description, type)
                name = ""
                priceField = ""
                image = ""
                description = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save product")
        }
    }
}

@Composable
private fun ProductTypeSelector(selectedType: String, onTypeSelected: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DatabaseHelper.PRODUCT_TYPES.chunked(2).forEach { rowTypes ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTypes.forEach { option ->
                    val isSelected = option == selectedType
                    if (isSelected) {
                        Button(
                            onClick = { onTypeSelected(option) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(option)
                        }
                    } else {
                        OutlinedButton(
                            onClick = { onTypeSelected(option) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(option)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview() {
    ProductForm { _, _, _, _, _ -> }
}
