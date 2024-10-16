@file:OptIn(ExperimentalFoundationApi::class)

package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Get profile data on initial load
    LaunchedEffect(Unit) {
        viewModel.getProfile { errorMessage ->
            println("Error fetching profile: $errorMessage")
        }
    }

    // Automatically scroll the pager every 3 seconds if banners are available
    LaunchedEffect(banners) {
        if (banners.isNotEmpty()) {
            while (true) {
                delay(3000) // Delay for 3 seconds
                coroutineScope.launch {
                    val nextPage = (pagerState.currentPage + 1) % banners.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hello, ${profile?.data?.name ?: ""}",
                        modifier = Modifier.padding(start = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { navigator.push(SearchScreen()) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color(0xFFFF9800),
                tonalElevation = 8.dp
            ) {
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(FavoritesScreen()) }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorites",
                        tint = Color.Black
                    )
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(CartsScreen()) }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.Black
                    )
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigator.push(Settings()) }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.Black
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .scrollable(state = scrollState, orientation = Orientation.Vertical),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Banners Section
                if (banners.isNotEmpty()) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxHeight(0.3f)
                            .padding(vertical = 8.dp),
                        pageSize = PageSize.Fill,
                        pageSpacing = 7.dp
                    ) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(8.dp, RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                        ) {
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

                // Categories Title
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                // Categories Section
                if (categories.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(16.dp)
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
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
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
            .clip(RoundedCornerShape(12.dp))
            .clickable { navigator.push(CategoryProductsScreen(categoryId = id, name = name)) }
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.image),
            contentDescription = category.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
