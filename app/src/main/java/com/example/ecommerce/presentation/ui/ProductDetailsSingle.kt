package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.presentation.viewModel.ProductDetailsViewModel
/*
class ProductDetailsScreen(
    private val productId: Int
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProductDetailsViewModel = hiltViewModel()

        // استدعاء دالة لجلب تفاصيل المنتج عند بدء الشاشة
        LaunchedEffect(productId) {
            viewModel.getProductDetails(productId)
        }

        // مراقبة حالة تفاصيل المنتج
        val productState by viewModel.productDetails.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                productState != null && productState!!.data.data.isNotEmpty() -> {
                    // العثور على المنتج بناءً على معرف المنتج
                    val product = productState!!.data.data.find { it.id == productId }
                    product?.let {
                        ProductDetailCard(it)
                    } ?: run {
                        Text(
                            text = "المنتج غير موجود.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    Text(
                        text = "جارٍ تحميل تفاصيل المنتج...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
*/
class ProductDetailsScreen(
    private val productId: Int
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProductDetailsViewModel = hiltViewModel()

        // استدعاء لجلب تفاصيل المنتج عند بدء الشاشة
        LaunchedEffect(productId) {
            viewModel.getProductDetails(productId)
        }

        // مراقبة حالة تفاصيل المنتج
        val productState by viewModel.productDetails.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // التعامل مع حالة البيانات
            productState?.data?.data?.find { it.id == productId }?.let { product ->
                ProductDetailCard(product)
            } ?: run {
                DisplayLoadingOrErrorMessage(productState)
            }
        }
    }
}

@Composable
fun DisplayLoadingOrErrorMessage(productState: Products?) {
    if (productState == null) {
        Text(
            text = "جارٍ تحميل تفاصيل المنتج...",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = "المنتج غير موجود.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun ProductDetailCard(product: ProductItemSmall) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(product.image),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "السعر: ${product.price} ج.م.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
