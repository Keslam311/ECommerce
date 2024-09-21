package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
import com.example.ecommerce.presentation.viewModel.SearchViewModel

class CategoryProductsScreen(
    private val categoryId: Int
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: CategoryProductsViewModel = hiltViewModel()
        val searchViewModel: SearchViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        var searchText by remember { mutableStateOf("") }
        LaunchedEffect(categoryId) {
            viewModel.setCategoryId(categoryId)
        }

//        val productsState by viewModel.categoryProducts.collectAsState()
        Scaffold(topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
            )
        }) { padding ->
            if (searchText.isEmpty()) {
                val productsState by viewModel.categoryProducts.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    OutlinedTextField(value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("Search Products") })
                    if (productsState?.data?.data != null) {
                        Box {
                            LazyVerticalGrid(columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                content = {
                                    when {
                                        productsState?.data?.data != null && productsState!!.status -> {
                                            val products = productsState!!.data.data
                                            items(products) { product ->
                                                ProductBox(product)
                                            }
                                        }

                                        productsState?.status == false -> {
                                            item {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(
                                                        text = "Failed to load products.",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(16.dp),
                                                        textAlign = TextAlign.Center,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                        }

                                    }
                                }

                            )
                        }

                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
            } else {
                searchViewModel.getProductsSearch(searchText)
                val productsState by searchViewModel.productSearch.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    OutlinedTextField(value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("Search Products") })
                    if (productsState?.data?.data != null) {
                        Box {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()
                            ) {
                                when {
                                    productsState?.data?.data != null && productsState!!.status -> {
                                        val products = productsState!!.data.data
                                        items(products) { product ->
                                            ProductBox(product)
                                        }
                                    }

                                    productsState?.status == false -> {
                                        item {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "Failed to load products.",
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp),
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                    }                }
            }
        }
    }
}

@Composable
fun ProductBox(product: ProductItemSmall) {
    val navigator = LocalNavigator.currentOrThrow
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navigator.push(ProductDetailsScreen(product.id))
        }
        .padding(vertical = 4.dp)
        .background(MaterialTheme.colorScheme.surface) // إضافة خلفية
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: \$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
