@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.CategoryItem
import com.example.ecommerce.presentation.viewModel.BannersViewModel
import com.example.ecommerce.presentation.viewModel.CategoriesViewModel
import com.example.ecommerce.presentation.viewModel.ProfileViewModel

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

        HomeScreenContent(bannersViewModel, categoriesViewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
    val viewModel: ProfileViewModel = hiltViewModel()
    val profile by viewModel.profileState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProfile { errorMessage ->
            println("Error fetching profile: $errorMessage")
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(FavoritesScreen()) }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(CartsScreen()) }) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(Settings()) }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }

            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Hello, ${profile?.data?.name}",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    )

                    Icon(
                        imageVector = Icons.Default.Search,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(8.dp)
                            .clickable(
                                indication = null, // إخفاء تأثير الضغط الافتراضي
                                interactionSource = remember { MutableInteractionSource() } // إخفاء التأثيرات
                            ) {
                                navigator.push(SearchScreen())
                            },
                        contentDescription = "Search",
                    )
                }

                // Banners Section
                if (banners.isNotEmpty()) {
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
                } else {
                    LoadingState()
                }

                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

                // Categories Section
                if (categories.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(categories) { category ->
                            CategoryItem(category = category, category.id, category.name)
                        }
                    }
                } else {
                    Text(
                        text = "No categories available.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}


@Composable
fun CategoryItem(category: CategoryItem, id: Int, name: String) {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigator.push(CategoryProductsScreen(categoryId = id, name = name)) },
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

