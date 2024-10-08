/*
package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel

class FavoritesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: CategoryProductsViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val productsState by viewModel.allProducts.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getAllProduct()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Favorite Products") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    },
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Content of the screen
                FavoritesGridState(
                    productsState = productsState,
                    onProductClick = { product ->
                        navigator.push(FavoritesProductsDetails(product))
                    }
                )
                // Show loading indicator if products are loading
                if (productsState == null) {
                    FavoriteLoadingState()
                }
            }
        }
    }

    @Composable
    fun FavoritesGridState(
        productsState:List<ProductItemSmall>?,
        onProductClick: (ProductItemSmall) -> Unit
    ) {
        when {
            productsState!= null  -> {
                FavoritesGrid(
                    products = productsState,
                    onProductClick = onProductClick
                )
            }

            else -> {
                FavoriteLoadingState()
            }
        }
    }

    @Composable
    fun FavoritesGrid(
        products: List<ProductItemSmall>,
        onProductClick: (ProductItemSmall) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products.filter { it.in_favorites }) { product ->
                key(product.id) {
                    FavoriteProductBox(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }

    @Composable
    fun FavoriteProductBox(product: ProductItemSmall, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: \$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }

    @Composable
    fun FavoriteLoadingState() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }



}

*/


package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.CartViewModel
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
import com.example.ecommerce.util.PreferencesManager
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
class CartScreen : Screen {

    private var productList: MutableList<ProductItemSmall> by mutableStateOf(mutableListOf())

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: CategoryProductsViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val productsState by viewModel.allProducts.collectAsState()
        var showNoCart by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            viewModel.getAllProduct()
        }
        LaunchedEffect(productsState) {
            if (productsState != null) {
                productList = productsState!!.toMutableStateList()
            }
        }
        LaunchedEffect(Unit) {
            delay(15000) // 15 seconds
            showNoCart = true
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Cart Products") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Home"
                            )
                        }
                    },
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (productList.isNotEmpty()) {
                    // Display the cart grid if there are products
                    CartGridState(
                        context = context,
                        productsState = productList,
                        onProductClick = { product ->
                            navigator.push(FavoritesProductsDetails(product))
                        },
                    )
                } else if (showNoCart) {
                    // Show the "No cart product yet" message in the middle of the screen
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No product in cart yet",
                            textAlign = TextAlign.Center
                        )
                    }
                    when {
                        productList.isNotEmpty() -> {
                            // Display the favorites grid if there are products
                            CartGridState(
                                context = context,
                                productsState = productList,
                                onProductClick = { product ->
                                    navigator.push(FavoritesProductsDetails(product))
                                },
                            )
                        }
                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No product in cart yet",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                } else {
                    // Show the loading indicator while products are being fetched
                    CartLoadingState()
                }
            }
        }

    }

    @Composable
    fun CartGridState(
        context: Context,
        productsState: MutableList<ProductItemSmall>?,
        onProductClick: (ProductItemSmall) -> Unit,
    ) {
        when {
            productsState != null -> {
                CartGrid(
                    context = context,
                    products = productsState,
                    onProductClick = onProductClick
                )
            }

            else -> {
                CartLoadingState()
            }
        }
    }

    @Composable
    fun CartGrid(
        context: Context,
        products: MutableList<ProductItemSmall>,
        onProductClick: (ProductItemSmall) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products.filter { it.in_cart }) { product ->
                key(product.id) {
                    CartProductBox(
                        context = context,
                        product = product,
                        onClick = { onProductClick(product) },
                        productIndex = products.indexOf(product)
                    )
                }
            }
        }
    }

    @Composable
    fun CartProductBox(
        context: Context,
        product: ProductItemSmall,
        onClick: () -> Unit,
        productIndex: Int
    ) {
        var isInCart by remember {
            mutableStateOf(
                PreferencesManager.isInCart(
                    context,
                    product.id.toString()
                )
            )
        }
        val CartViewModel: CartViewModel = hiltViewModel()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // Space between text and button
                ) {
                    Text(
                        text = "Price: \$${product.price}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    IconButton(onClick = {
                        isInCart = !isInCart // Toggle favorite state
                        PreferencesManager.setInCart(
                            context,
                            product.id.toString(),
                            isInCart
                        ) // Save to SharedPreferences
                        CartViewModel.cartAddOrDelete(product.id, onSuccess = {
                            Toast.makeText(
                                context,
                                if (isInCart) "Added to cart" else "Removed from cart",
                                Toast.LENGTH_SHORT
                            ).show()
                            productList.removeAt(productIndex)

                        }, onError = {
                            isInCart = !isInCart // Roll back favorite status on error
                            Toast.makeText(
                                context,
                                "Error occurred while updating favorites.",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "ShoppingCart",
                            tint = if (isInCart) Color.Blue else Color.Gray // Heart color based on favorite state
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun CartLoadingState() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

