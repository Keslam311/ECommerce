package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.ecommerce.util.PreferencesManager
import com.example.ecommerce.R
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel
import com.example.ecommerce.presentation.viewModel.FavoritesViewModel

class ProductDetailsScreen(
    private val product: ProductItemSmall // Directly using product instead of products
) : Screen {
    @Composable
    override fun Content() {
        ProductDetailCard(product)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailCard(product: ProductItemSmall) {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val categoryViewModel: CategoryProductsViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()

    // State to manage toast messages
    var toastMessage by remember { mutableStateOf("") }

    // State to manage if description is expanded or not
    var isExpanded by remember { mutableStateOf(false) }

    // Load favorite state from SharedPreferences
    val isFavoriteState = PreferencesManager.isFavorite(context, product.id.toString())
    var isFavorite by remember { mutableStateOf(isFavoriteState) }

    // Handle showing the toast messages
    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            toastMessage = "" // Clear message after showing
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Back Arrow
            IconButton(
                onClick = { navigator.pop() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_home)
                )
            }

            // Product Image
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            // Product Information and Favorite Icon
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Product Name and Favorite Icon in a Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        PreferencesManager.setFavorite(
                            context,
                            product.id.toString(),
                            isFavorite
                        ) // Convert to String and save to SharedPreferences
                        favoritesViewModel.favoriteAddOrDelete(product.id, onSuccess = {
                            toastMessage =
                                if (isFavorite) "Added to favorites" else "Removed from favorites"
                            categoryViewModel.getAllProduct()
                        }, onError = {
                            isFavorite = !isFavorite // Roll back favorite status on error
                            toastMessage = "Error occurred while updating favorites."
                        })
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.favorite),
                            tint = if (isFavorite) Color.Red else Color.Gray, // Change color based on favorite status

                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                // Product Description with "Read more"
                val formattedDescription =
                    product.description.replace(Regex("\\. (?=[A-Za-z])"), ".\n")

                Text(
                    text = formattedDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                if (product.description.length > 100) {
                    Text(
                        text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(
                            id = R.string.read_more
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable { isExpanded = !isExpanded }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${stringResource(id = R.string.price_label)}: ${product.price}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}
