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
class CategoryProductsViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _categoryProducts = MutableStateFlow<Products?>(null)
    val categoryProducts: StateFlow<Products?> get() = _categoryProducts
    private var currentCategoryId: Int? = null

    fun setCategoryId(id: Int) {
        if (currentCategoryId != id) {
            currentCategoryId = id
            getProducts()
        }
    }

    private fun getProducts() {
        currentCategoryId?.let { id ->
            viewModelScope.launch {
                try {
                    val response = apiService.getProductsByCategory(id)
                    if (response.isSuccessful) {
                        _categoryProducts.value = response.body()
                    } else {
                        Log.w("Error", "Response not successful: ${response.code()}")
                        // يمكنك تحديث الحالة لتعكس الخطأ في واجهة المستخدم
                        _categoryProducts.value = null
                    }
                } catch (ex: Exception) {
                    Log.w("Error", "Exception occurred: $ex")
                    // يمكنك تحديث الحالة لتعكس الاستثناء في واجهة المستخدم
                    _categoryProducts.value = null
                }
            }
        }
    }
}
