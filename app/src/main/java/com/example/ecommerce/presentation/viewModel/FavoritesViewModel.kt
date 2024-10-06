package com.example.ecommerce.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoritesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    // StateFlow to hold the list of favorite products
    private val _favoriteProducts = MutableStateFlow<List<ProductItemSmall>>(emptyList())
    val favoriteProducts: StateFlow<List<ProductItemSmall>> get() = _favoriteProducts

    // Function to get favorite products
    fun getFavorites() {
        viewModelScope.launch {
            val response = apiService.getFavorites()
            if (response.isSuccessful) {
                _favoriteProducts.value = response.body() ?: emptyList()
            } else {
                _favoriteProducts.value = emptyList()
            }
        }
    }

    // Function to add or remove a product from favorites
    fun toggleFavorite(product: ProductItemSmall) {
        viewModelScope.launch {
            val updatedProduct = product.copy(in_favorites = !product.in_favorites)
            if (updatedProduct.in_favorites) {
                val response = apiService.addToFavorites(updatedProduct)
                if (response.isSuccessful) {
                    _favoriteProducts.value = _favoriteProducts.value + updatedProduct
                }
            } else {
                val response = apiService.removeFromFavorites(product.id)
                if (response.isSuccessful) {
                    _favoriteProducts.value = _favoriteProducts.value.filter { it.id != product.id }
                }
            }
        }
    }
}