package com.example.ecommerce.presentation.ui

import android.content.Context
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.CategoryItem
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.interceptor.AuthInterceptor
import com.example.ecommerce.presentation.viewModel.CategoriesViewModel
import com.example.ecommerce.presentation.viewModel.HomeViewModel
import javax.inject.Inject


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        // Get the HomeViewModel and CategoriesViewModel instances
        val homeViewModel: HomeViewModel = hiltViewModel()
        val categoriesViewModel: CategoriesViewModel = hiltViewModel()
        // Use the HomeScreenContent composable
        HomeScreenContent(viewModel = homeViewModel, categoriesViewModel = categoriesViewModel, )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(viewModel: HomeViewModel , categoriesViewModel: CategoriesViewModel) {
    val productsResponse = viewModel.product.collectAsState()
    val products = productsResponse.value?.data?.data ?: emptyList()
    val categoriesResponse = categoriesViewModel.categories.collectAsState()
    val categories = categoriesResponse.value?.data?.data ?: emptyList()

  Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
  ) {

  }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = { /* Add any top bar actions if needed */ }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Navigate to Home Screen */ }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Navigate to Favorites Screen */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Navigate to Cart Screen */ }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Navigate to Profile Screen */ }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                // Search Bar
                var searchText by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        // viewModel.searchProducts(newText) // Assuming there's a search function in ViewModel
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search Products") }
                )

                // Categories Section
                Text(
                    text = "Categories",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )

                // LazyRow for Categories
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between items
                ) {
                    items(categories) { category ->
                        CategoryItem(category = category)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Popular Products Section
                Text(
                    text = "Popular Products",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
                // LazyRow for Popular Products
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between items
                ) {
                    items(products) { product ->
                        ProductItem(product = product)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // See All Button
                Text(
                    text = "See All",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { /* Navigate to All Products Screen */ }
                        .align(Alignment.End)
                    ,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // New Arrivals Section
                Text(
                    text = "New Arrivals",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )

                // LazyRow for New Arrivals
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between items
                ) {
                    items(products) { product ->
                        ProductItemSmall(product = product)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // See All Button
                Text(
                    text = "See All",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { /* Navigate to All Products Screen */ }
                        .align(Alignment.End),
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    )
}

@Composable
fun CategoryItem(category: CategoryItem) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp)
            .clickable {},
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(category.image),
                contentDescription = category.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProductItem(product: ProductItemSmall) {
    Card(
        modifier = Modifier
            .width(180.dp) // Adjust width as needed
            .padding(8.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .height(120.dp) // Adjust height as needed
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = product.name,
//                fontWeight = FontWeight.Bold,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Price: $${product.price}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProductItemSmall(product: ProductItemSmall) {
    Card(
        modifier = Modifier
            .width(120.dp) // Adjust width as needed
            .padding(8.dp)
            .clickable { /* Navigate to Product Details Screen */ },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .height(80.dp) // Adjust height as needed
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Price: $${product.price}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}