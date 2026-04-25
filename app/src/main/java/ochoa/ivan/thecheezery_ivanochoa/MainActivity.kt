package ochoa.ivan.thecheezery_ivanochoa

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ochoa.ivan.thecheezery_ivanochoa.data.CombosDAO
import ochoa.ivan.thecheezery_ivanochoa.data.DatabaseHelper
import ochoa.ivan.thecheezery_ivanochoa.data.ProductDAO
import ochoa.ivan.thecheezery_ivanochoa.screen.AddComboScreen
import ochoa.ivan.thecheezery_ivanochoa.screen.AddProductScreen
import ochoa.ivan.thecheezery_ivanochoa.screen.MenuScreen
import ochoa.ivan.thecheezery_ivanochoa.screen.ProductsScreen
import ochoa.ivan.thecheezery_ivanochoa.screen.WelcomeScreen
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme
import ochoa.ivan.thecheezery_ivanochoa.viewmodel.ProductsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCheezery_IvanOchoaTheme {
                TheCheezeryApp()
            }
        }
    }
}

@Composable
fun TheCheezeryApp() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val viewModel = remember {
        val databaseHelper = DatabaseHelper(context)
        ProductsViewModel(
            productDAO = ProductDAO(databaseHelper),
            combosDAO = CombosDAO(databaseHelper),
            contextLocal = context
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CheezeryRoute.Welcome
        ) {
            composable(CheezeryRoute.Welcome) {
                WelcomeScreen(
                    innerPadding = innerPadding,
                    onStart = {
                        navController.navigate(CheezeryRoute.Menu) {
                            popUpTo(CheezeryRoute.Welcome) { inclusive = true }
                        }
                    }
                )
            }

            composable(CheezeryRoute.Menu) {
                MenuScreen(
                    innerPadding = innerPadding,
                    onProductTypeSelected = { type ->
                        navController.navigate(CheezeryRoute.products(type))
                    },
                    onAddProduct = {
                        navController.navigate(CheezeryRoute.AddProduct)
                    },
                    onCombos = {
                        navController.navigate(CheezeryRoute.AddCombo)
                    }
                )
            }

            composable(CheezeryRoute.AddProduct) {
                AddProductScreen(
                    innerPadding = innerPadding,
                    onSaveProduct = viewModel::onSaveProduct,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = CheezeryRoute.Products,
                arguments = listOf(navArgument(CheezeryRoute.ProductTypeArg) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val productType = backStackEntry.arguments
                    ?.getString(CheezeryRoute.ProductTypeArg)
                    .orEmpty()

                LaunchedEffect(productType) {
                    viewModel.loadProducts(productType)
                }

                ProductsScreen(
                    innerPadding = innerPadding,
                    type = productType,
                    products = viewModel.products,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(CheezeryRoute.AddCombo) {
                LaunchedEffect(Unit) {
                    viewModel.loadProducts()
                    viewModel.loadCombos()
                }

                AddComboScreen(
                    innerPadding = innerPadding,
                    products = viewModel.products,
                    combos = viewModel.combos,
                    onSaveCombo = viewModel::onSaveCombo,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

private object CheezeryRoute {
    const val Welcome = "welcome"
    const val Menu = "menu"
    const val AddProduct = "addProduct"
    const val AddCombo = "addCombo"
    const val ProductTypeArg = "type"
    const val Products = "products/{$ProductTypeArg}"

    fun products(type: String): String = "products/${Uri.encode(type)}"
}
