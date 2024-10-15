package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.AddCartRequest
import com.example.ecommerce.data.model.CartItem
import com.example.ecommerce.presentation.viewModel.GetCartsViewModel
import com.example.ecommerce.util.PreferencesManager

class CartsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: GetCartsViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        // Observe cart data
        val cartsState by viewModel.carts.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getCarts() // Fetch cart data when screen loads
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "My Cart") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Home"
                            )
                        }
                    },
                )
            },
            bottomBar = {
                if (cartsState?.data?.cart_items?.isNotEmpty() == true) {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White
                    ) {
                        BuyButton(onClick = {
                            // Implement buy action
                            navigator.push(SuccessfulScreen())
                            Toast.makeText(context, "Proceeding to payment", Toast.LENGTH_SHORT)
                                .show()
                        })
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when {
                    cartsState == null -> {
                        // Show loading state while data is being fetched
                        CartLoadingState()
                    }

                    cartsState?.data?.cart_items?.isNotEmpty() == true -> {
                        // Show cart items if data is available
                        CartItemsList(
                            cartItems = cartsState?.data?.cart_items.orEmpty(),
                            total = cartsState?.data?.total ?: 0.0,
                            context = context,
                            viewModel = viewModel
                        )
                    }

                    else -> {
                        // Show empty cart message if no items are found
                        EmptyCartView()
                    }
                }
            }
        }
    }


    @Composable
    fun CartItemsList(
        cartItems: List<CartItem>,
        total: Double,
        context: Context,
        viewModel: GetCartsViewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between items
        ) {
            items(cartItems) { cartItem ->
                EnhancedCartItemView(
                    cartItem = cartItem,
                    context = context,
                    viewModel = viewModel,
                    quantity = cartItem.quantity
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Price : \$$total",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @Composable
    fun EnhancedCartItemView(
        cartItem: CartItem,
        context: Context,
        viewModel: GetCartsViewModel,
        quantity: Int
    ) {
        var quantity by remember { mutableIntStateOf(quantity) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                .shadow(4.dp, MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Product Image
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(cartItem.product.image),
                        contentDescription = cartItem.product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Product Details
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = cartItem.product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${quantity} x \$${cartItem.product.price}",
                        fontSize = 14.sp
                    )
                    if (cartItem.product.discount > 0) {
                        Text(
                            text = "Discount: ${cartItem.product.discount}%",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "\$${
                            String.format(
                                "%.1f",
                                (cartItem.product.price - (cartItem.product.price * cartItem.product.discount / 100)) * quantity
                            )
                        }",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    // Quantity Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        IconButton(onClick = {
                            if (quantity > 1) {
                                quantity--
                                viewModel.updateCarts(cartItem.id, quantity, onSuccess = {
                                }, onError = {})
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Decrease quantity")
                        }

                        Text(
                            text = quantity.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        IconButton(onClick = {
                            quantity++
                            viewModel.updateCarts(cartItem.id, quantity = quantity, onSuccess = {
                            }, onError = {})
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Increase quantity")
                        }
                    }
                }

                // Delete Button
                IconButton(onClick = {
                    viewModel.addCartsOrDeleteCarts(cartItem.product.id, onSuccess = {
                        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show()
                        viewModel.getCarts()
                        PreferencesManager.setCartStatus(context, cartItem.product.id.toString(), false)
                    }, onError = {
                        Toast.makeText(context, "Error removing item from cart", Toast.LENGTH_SHORT)
                            .show()
                    })
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete item",
                        tint = Color.Red
                    )
                }
            }
        }
    }

    @Composable
    fun CartLoadingState() {
        // Loading spinner or message can be shown here
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun EmptyCartView() {
        // Show message for empty cart
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Your cart is empty.", fontSize = 20.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun BuyButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Buy")
    }
}