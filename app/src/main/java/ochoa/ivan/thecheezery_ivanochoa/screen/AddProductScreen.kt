package ochoa.ivan.thecheezery_ivanochoa.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ochoa.ivan.thecheezery_ivanochoa.components.ProductForm
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Dusty_white
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme

@Composable
fun AddProductScreen(
    innerPadding: PaddingValues,
    onSaveProduct: (name: String, price: Float, image: String, description: String, type: String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dusty_white)
            .padding(innerPadding)
    ) {
        ProductForm(onSaveProduct = onSaveProduct)
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text("Back to menu")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductScreenPreview() {
    TheCheezery_IvanOchoaTheme {
        AddProductScreen(
            innerPadding = PaddingValues(),
            onSaveProduct = { _, _, _, _, _ -> },
            onBack = {}
        )
    }
}
