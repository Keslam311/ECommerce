@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.CategoryItem
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.interceptor.AuthInterceptor
import com.example.ecommerce.presentation.viewModel.BannersViewModel
import com.example.ecommerce.presentation.viewModel.CategoriesViewModel
import com.example.ecommerce.presentation.viewModel.HomeViewModel
import javax.inject.Inject


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        // Get the HomeViewModel and CategoriesViewModel instances
        val homeViewModel: HomeViewModel = hiltViewModel()
        val categoriesViewModel: CategoriesViewModel = hiltViewModel()
        val bannersViewModel: BannersViewModel = hiltViewModel()
        // Use the HomeScreenContent composable
        HomeScreenContent(
            viewModel = homeViewModel,
            categoriesViewModel = categoriesViewModel,
            bannersViewModel = bannersViewModel
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    bannersViewModel: BannersViewModel,
    viewModel: HomeViewModel,
    categoriesViewModel: CategoriesViewModel
) {
    val productsResponse = viewModel.product.collectAsState()
    val products = productsResponse.value?.data?.data ?: emptyList()
    val categoriesResponse = categoriesViewModel.categories.collectAsState()
    val categories = categoriesResponse.value?.data?.data ?: emptyList()
    val bannersResponse = bannersViewModel.banners.collectAsState()
    val banners = bannersResponse.value?.data ?: emptyList()
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val navigator = LocalNavigator.currentOrThrow
    Log.d("HomeScreen", "Fuck: $banners")

    Scaffold(

        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(modifier = Modifier.weight(1f),onClick = { /* Navigate to Home Screen */ }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(modifier = Modifier.weight(1f),onClick = { /* Navigate to Favorites Screen */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                }
                IconButton(modifier = Modifier.weight(1f),onClick = { /* Navigate to Cart Screen */ }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
                IconButton(modifier = Modifier.weight(1f),onClick = { navigator.push(Settings()) }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Cart")
                }

            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                //Banners here
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxHeight(0.3f),
                    pageSize = PageSize.Fill,
                    pageSpacing = 7.dp,

                    ) { index ->

                        Card {
                            AsyncImage(
                                model = banners[index].image,
                                contentDescription = "Banner",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillHeight,

                            )
                        }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "Categories",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Categories Section
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        items(categories) { category ->
                            CategoryItem(category = category)
                        }
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
            .clickable() {
                navigator.push(CategoryProductsScreen(categoryId = category.id))
            },
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