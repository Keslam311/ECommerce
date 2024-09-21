/*
//package com.example.ecommerce.presentation.ui
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import androidx.hilt.navigation.compose.hiltViewModel
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import coil.compose.rememberAsyncImagePainter
//import com.example.ecommerce.data.model.ProductItemSmall
//import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//// إذا كنت تستخدم Voyager، يجب أن تنفذ واجهة Screen
//class CategoryProductsScreen(
//    private val categoryId: Int
//) : Screen {
//
//    @Composable
//    override fun Content() {
//        // الحصول على الـ ViewModel باستخدام Hilt
//        val viewModel: CategoryProductsViewModel = hiltViewModel()
//
//        // تعيين معرف الفئة عند تغييره فقط
//        LaunchedEffect(categoryId) {
//            viewModel.setCategoryId(categoryId)
//        }
//
//        // مراقبة حالة المنتجات
//        val productsState by viewModel.categoryProducts.collectAsState()
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            when {
//                productsState?.data?.data != null && productsState!!.status -> {
//                    // حالة النجاح مع البيانات
//                    val products = productsState!!.data.data
//                    items(products) { product ->
//                        ProductCard(product)
//                    }
//                }
//                productsState?.status == false -> {
//                    item {
//                        Text(
//                            text = "Failed to load products.",
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            textAlign = TextAlign.Center,
//                            color = MaterialTheme.colorScheme.error
//                        )
//                    }
//                }
//                else -> {
//                    item {
//                        Text(
//                            text = "Loading products...",
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProductCard(product: ProductItemSmall) {
//    val navigator = LocalNavigator.currentOrThrow
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                navigator.push(ProductDetailsScreen(product.id))
//            }
//            .padding(vertical = 4.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Image(
//                painter = rememberAsyncImagePainter(product.image),
//                contentDescription = product.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = product.name,
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Price: \$${product.price}",
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.secondary
//            )
//        }
//    }
//}
 */
package com.example.ecommerce.presentation.ui

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    },
                )
            }
        ) { padding ->
            if (searchText.isEmpty()) {
                val productsState by viewModel.categoryProducts.collectAsState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                            searchViewModel.getProductsSearch(newText) // Assuming there's a search function in ViewModel
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("Search Products") }
                    )
                    Box {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize()
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

                                else -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                LaunchedEffect(searchText) {
                    searchViewModel.getProductsSearch(searchText)
                }
                val productsState by searchViewModel.productSearch.collectAsState()
                Log.d("zamzam", "Search text: ${productsState?.data}")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { newText ->
                            searchText = newText
                            searchViewModel.getProductsSearch(newText) // Assuming there's a search function in ViewModel
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        placeholder = { Text("Search Products") }
                    )
                    Box {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize()
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

                                else -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductBox(product: ProductItemSmall) {
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = Modifier
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
