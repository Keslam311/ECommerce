/*
//package com.example.ecommerce.presentation.ui
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import com.example.ecommerce.presentation.viewModel.SearchViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//class SearchProductsScreen(
//) : Screen {
//    @Composable
//    override fun Content() {
//        val searchViewModel: SearchViewModel = hiltViewModel()
//        val navigator = LocalNavigator.currentOrThrow
//        var searchText by remember { mutableStateOf("") }
//
//        // استدعاء getProductsSearch فقط عند وجود نص
//        LaunchedEffect(searchText) {
//            if (searchText.isNotBlank()) {
//                searchViewModel.getProductsSearch(searchText)
//            } else {
//                searchViewModel.getProductsSearch()
//            }
//        }
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Search Products") },
//                    navigationIcon = {
//                        IconButton(onClick = { navigator.pop() }) {
//                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
//                        }
//                    },
//                )
//            }
//        ) { padding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//            ) {
//                OutlinedTextField(
//                    value = searchText,
//                    onValueChange = { newText ->
//                        searchText = newText
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
//                    placeholder = { Text("Search Products") },
//                    shape = RoundedCornerShape(8.dp)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // عرض نتائج البحث فقط إذا كان هناك نص في مربع البحث
//                if (searchText.isNotBlank()) {
//                    val searchState by searchViewModel.productSearch.collectAsState()
//                    ProductGridState(
//                        productsState = searchState,
//                        onProductClick = { productId ->
//                            navigator.push(ProductDetailsScreen(productId))
//                        }
//                    )
//                } else {
//                    Text(
//                        text = " Please enter a search term",
//                        modifier = Modifier.padding(16.dp),
//                        color = Color.Gray
//                    )
//                }
//            }
//        }
//    }
//}
*/
package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.presentation.viewModel.SearchViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
class SearchProductsScreen : Screen {
    @Composable
    override fun Content() {
        val searchViewModel: SearchViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        var searchText by remember { mutableStateOf("") }
        val products by searchViewModel.productSearch.collectAsState()

        // Fetch results when the search text changes
        LaunchedEffect(searchText) {
            if (searchText.isNotBlank()) {
                searchViewModel.getProductsSearch(searchText)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Search Products") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    },
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.White),
                    placeholder = { Text("Search Products") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Show search results only if there is text in the search box
                if (searchText.isNotBlank()) {
                    if (products?.data?.data?.isNotEmpty() == true) {
                        ProductGridState(
                            productsState = products,
                            onProductClick = { productId ->
                                navigator.push(ProductDetailsScreen(productId))
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No products found for '$searchText'",
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Please enter a search term",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
