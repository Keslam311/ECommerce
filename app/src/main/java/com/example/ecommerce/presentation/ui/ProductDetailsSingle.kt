package com.example.ecommerce.presentation.ui

/*
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
import com.example.ecommerce.presentation.viewModel.ProductDetailsViewModel
import cafe.adriel.voyager.navigator.currentOrThrow

class ProductDetailsScreen(
    private val products: ProductItemSmall
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProductDetailsViewModel = hiltViewModel()

        // Trigger product details fetching on screen start
        LaunchedEffect(products.id) {
            viewModel.getProductDetails(products.id)
        }

        // Observe product details state
        val productState by viewModel.productDetails.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            products.let { product ->
                ProductDetailCard(product, product.id)
            } ?: run {
                DisplayLoadingOrErrorMessage(productState)
            }
        }
    }
}

@Composable
fun DisplayLoadingOrErrorMessage(productState: Products?) {
    if (productState == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Text(
            text = "المنتج غير موجود.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailCard(product: ProductItemSmall, productId: Int) {
    val navigator = LocalNavigator.currentOrThrow
    var isFavorite by remember { mutableStateOf(false) } // Changed to var for recomposition
    val context = LocalContext.current
    val categoryViewModel: CategoryProductsViewModel = hiltViewModel()

    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Home"
                    )
                }
            },
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState()), // Add vertical scroll
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Row to hold the heart icon and product details
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                // Heart Icon
                IconButton(onClick = {
                    isFavorite = !isFavorite
                    categoryViewModel.favoriteAddOrDelete(productId, onSuccess = {
                        Toast.makeText(
                            context,
                            if (isFavorite) "Added to Favorites" else "Removed from Favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        categoryViewModel.getAllProduct() // Refresh product list after action
                    }, onError = {
                        Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                    })
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) { // Weight to allow text to fill remaining space
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.description, style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "السعر: ${product.price} ج.م.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
*/
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.ui.res.stringResource
import com.example.ecommerce.R

class ProductDetailsScreen(
    private val product: ProductItemSmall // Directly using product instead of products
) : Screen {
    @Composable
    override fun Content() {
        ProductDetailCard(product)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailCard(product: ProductItemSmall) {
    val navigator = LocalNavigator.currentOrThrow
    var isFavorite by remember { mutableStateOf(product.in_favorites) }
    val context = LocalContext.current
    val categoryViewModel: CategoryProductsViewModel = hiltViewModel()

    // State to manage toast messages
    var toastMessage by remember { mutableStateOf("") }

    // Handle showing the toast messages
    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            toastMessage = "" // Clear message after showing
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = product.name) },
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_to_home)
                    )
                }
            },
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    isFavorite = !isFavorite // Update UI immediately
                    categoryViewModel.favoriteAddOrDelete(product.id, onSuccess = {
                        toastMessage = if (isFavorite) "Added to favorites"
                        else "Removed from favorites"

                        categoryViewModel.getAllProduct() // Fetch updated products after action
                    }, onError = {
                        // Rollback state if error occurs
                        isFavorite =
                            !isFavorite // Roll back the favorite status if there's an error
                        toastMessage = "Error occurred while updating favorites."
                    })
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "السعر: ${product.price} ج.م.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

