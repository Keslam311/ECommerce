package com.example.ecommerce.presentation.ui
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.FavoritesViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit // Callback for navigating to product details
) {
    // Collect favorite products from the ViewModel
    val favoriteProducts by favoritesViewModel.favoriteProducts.collectAsState(initial = emptyList())

    // UI layout for the Favorites screen
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },

            )
        }
    ) { paddingValues ->
        // Display a loading state while fetching favorites
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorite products yet!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(favoriteProducts) { product ->
                    ProductBox(
                        product = product,
                        onClick = { onProductClick(product.id) }, // Navigate to product details
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}