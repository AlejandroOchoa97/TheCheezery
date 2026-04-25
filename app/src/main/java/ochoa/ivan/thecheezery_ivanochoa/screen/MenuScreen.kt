package ochoa.ivan.thecheezery_ivanochoa.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochoa.ivan.thecheezery_ivanochoa.R
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Brighter_Pink
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Dusty_white
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Less_Purple
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Pinky
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Very_purple

@Composable
fun MenuScreen(
    innerPadding: PaddingValues,
    onProductTypeSelected: (String) -> Unit,
    onAddProduct: () -> Unit,
    onCombos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dusty_white)
            .padding(innerPadding)
            .padding(horizontal = 22.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(28.dp))
        Image(
            painter = painterResource(R.drawable.grupo_2),
            contentDescription = "The Cheezery",
            modifier = Modifier
                .fillMaxWidth()
                .height(126.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(20.dp))

        MenuButtonGrid(
            onProductTypeSelected = onProductTypeSelected,
            onAddProduct = onAddProduct,
            onCombos = onCombos
        )
    }
}

@Composable
private fun MenuButtonGrid(
    onProductTypeSelected: (String) -> Unit,
    onAddProduct: () -> Unit,
    onCombos: () -> Unit
) {
    val firstGradient = Brush.verticalGradient(listOf(Brighter_Pink, Pinky))
    val secondGradient = Brush.verticalGradient(listOf(Pinky, Less_Purple))
    val thirdGradient = Brush.verticalGradient(listOf(Less_Purple, Very_purple))

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MenuTile(
                label = "Hot drinks",
                gradient = firstGradient,
                modifier = Modifier.weight(1f)
            ) { onProductTypeSelected(DatabaseHelper.TYPE_HOT_DRINKS) }
            MenuTile(
                label = "Cold drinks",
                gradient = firstGradient,
                modifier = Modifier.weight(1f)
            ) { onProductTypeSelected(DatabaseHelper.TYPE_COLD_DRINKS) }
        }
        Spacer(Modifier.height(1.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MenuTile(
                label = "Salties",
                gradient = secondGradient,
                modifier = Modifier.weight(1f)
            ) { onProductTypeSelected(DatabaseHelper.TYPE_SALTIES) }
            MenuTile(
                label = "Sweets",
                gradient = secondGradient,
                modifier = Modifier.weight(1f)
            ) { onProductTypeSelected(DatabaseHelper.TYPE_SWEETS) }
        }
        Spacer(Modifier.height(1.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MenuTile(
                label = "Combos",
                gradient = thirdGradient,
                modifier = Modifier.weight(1f),
                onClick = onCombos
            )
            MenuTile(
                label = "Add new product",
                gradient = thirdGradient,
                modifier = Modifier.weight(1f),
                onClick = onAddProduct
            )
        }
    }
}

@Composable
private fun MenuTile(
    label: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1.68f)
            .background(gradient)
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuScreenPreview() {
    TheCheezery_IvanOchoaTheme {
        MenuScreen(
            innerPadding = PaddingValues(),
            onProductTypeSelected = {},
            onAddProduct = {},
            onCombos = {}
        )
    }
}
