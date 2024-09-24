@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.CategoryItem
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.presentation.viewModel.BannersViewModel
import com.example.ecommerce.presentation.viewModel.CategoriesViewModel

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val categoriesViewModel: CategoriesViewModel = hiltViewModel()
        val bannersViewModel: BannersViewModel = hiltViewModel()

        // Fetching categories and banners
        LaunchedEffect(Unit) {
            bannersViewModel.getBanners()
            categoriesViewModel.getCategories()
        }

        HomeScreenContent(bannersViewModel,categoriesViewModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    bannersViewModel: BannersViewModel,
    categoriesViewModel: CategoriesViewModel
) {
    val categoriesResponse = categoriesViewModel.categories.collectAsState()
    val categories = categoriesResponse.value?.data?.data ?: emptyList()
    val bannersResponse = bannersViewModel.banners.collectAsState()
    val banners = bannersResponse.value?.data ?: emptyList()
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                IconButton(modifier = Modifier.weight(1f), onClick = { /* Navigate to Favorites Screen */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                }
                IconButton(modifier = Modifier.weight(1f), onClick = { /* Navigate to Cart Screen */ }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
                IconButton(modifier = Modifier.weight(1f), onClick = { navigator.push(Settings()) }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                // Banners
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxHeight(0.3f),
                    pageSize = PageSize.Fill,
                    pageSpacing = 7.dp
                ) { index ->
                    Card {
                        AsyncImage(
                            model = banners[index].image,
                            contentDescription = "Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center // This is the correct value
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Categories Section
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(categories) { category ->
                        CategoryItem(category = category)
                    }
                }
            }
        }
    )
}

@Composable
fun CategoryItem(category: CategoryItem) {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigator.push(CategoryProductsScreen(categoryId = category.id)) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.image),
            contentDescription = category.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth
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

@Composable
fun ProductItem(product: ProductItemSmall) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable { /* Navigate to Product Details Screen */ },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
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
            .width(120.dp)
            .padding(8.dp)
            .clickable { /* Navigate to Product Details Screen */ },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .height(80.dp)
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
