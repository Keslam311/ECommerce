package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _productDetails = MutableStateFlow<Products?>(null)
    val productDetails: StateFlow<Products?> get() = _productDetails

    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getProductDetails(productId)
                if (response.isSuccessful) {
                    _productDetails.value = response.body()
                } else {
                    Log.w("Error", "Response not successful: ${response.code()}")
                    _productDetails.value = null
                }
            } catch (ex: Exception) {
                Log.w("Error", "Exception occurred: $ex")
                _productDetails.value = null
            }
        }
    }
}
