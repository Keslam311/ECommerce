package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrRemoveCart
import com.example.ecommerce.data.model.CartRequest
import com.example.ecommerce.data.model.GetCarts
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    // Holds the cart items data
    private val _carts = MutableStateFlow<GetCarts?>(null)
    val carts: StateFlow<GetCarts?> get() = _carts

    // Holds all available products data
    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts

    // Holds the response of adding/removing cart items
    private val _cartAddOrDelete = MutableStateFlow<AddOrRemoveCart?>(null)
    val cartAddOrDelete: StateFlow<AddOrRemoveCart?> get() = _cartAddOrDelete

    // Loading state for cart operations
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Error message for handling errors
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Function to add or remove an item from the cart
    fun cartAddOrDelete(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Create a request with the product ID
                val request = CartRequest(product_id = productId)
                // Make the API call to add or delete from cart
                val response = apiService.addOrDeleteCart(request)

                // Handle the success response
                if (response.isSuccessful) {
                    _cartAddOrDelete.value = response.body()
                    onSuccess("Cart updated successfully.")
                } else {
                    _cartAddOrDelete.value = null
                    handleError("Failed to add/delete Cart: ${response.message()}")
                    onError("Failed to add or delete Cart.")
                }
            } catch (ex: Exception) {
                // Handle any exceptions during the API call
                _cartAddOrDelete.value = null
                handleError("Exception occurred: ${ex.localizedMessage}")
                onError("Exception: ${ex.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to log and handle errors
    private fun handleError(message: String) {
        Log.e("CartViewModel", message)
        _errorMessage.value = message
    }
}
