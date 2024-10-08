package com.example.ecommerce.presentation.viewModel
/*
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.ProductsDataClass
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {

    private val _categoryProducts = MutableStateFlow<Products?>(null)
    val categoryProducts: StateFlow<Products?> get() = _categoryProducts

    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts

    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    private var currentCategoryId: Int? = null

    fun setCategoryId(id: Int) {
        if (currentCategoryId != id) {
            currentCategoryId = id
            getProducts()
        }
    }
    init {
        getAllProduct()
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

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllProduct()
                if (response.isSuccessful) {
                    _allProducts.value = response.body()
                } else {

                }
            } catch (ex: Exception) {
                Log.w("Error", "Exception occurred: $ex")
                _allProducts.value = null
            }
        }


    }

    fun favoriteAddOrDelete(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val response =
                apiService.addOrDeleteFavorites(AddOrDeleteFavoriteRequest(product_id = productId))
            if (response.isSuccessful) {
                _favoriteAddOrDelete.value = response.body()
                getAllProduct()
                onSuccess(response.message())
            } else {
                _favoriteAddOrDelete.value = null
                onError(response.message())
            }
        }
    }

}*/
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {

    private val _categoryProducts = MutableStateFlow<Products?>(null)
    val categoryProducts: StateFlow<Products?> get() = _categoryProducts

    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts

//    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
//    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    private var currentCategoryId: Int? = null

    init {
        getAllProduct()
    }

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
                        handleError("Failed to get products by category: ${response.code()}")
                    }
                } catch (ex: Exception) {
                    handleError("Exception occurred while getting products by category: ${ex.localizedMessage}")
                }
            }
        }
    }

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllProduct()
                if (response.isSuccessful) {
                    _allProducts.value = response.body()
                } else {
                    handleError("Failed to get all products: ${response.code()}")
                }
            } catch (ex: Exception) {
                handleError("Exception occurred while getting all products: ${ex.localizedMessage}")
            }
        }
    }
/*
    fun favoriteAddOrDelete(
        productId: Int,
        onError:(String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response =
                    apiService.addOrDeleteFavorites(AddOrDeleteFavoriteRequest(product_id = productId))
                if (response.isSuccessful) {
                    _favoriteAddOrDelete.value = response.body()
                    getAllProduct()  // Refresh the list of all products after updating favorites
                    onSuccess(response.message())
                } else {
                    _favoriteAddOrDelete.value = null
                    onError("Failed to add or delete favorite: ${response.message()}")
                }
            } catch (ex: Exception) {
                _favoriteAddOrDelete.value = null
                onError("Exception occurred while adding or deleting favorite: ${ex.localizedMessage}")
            }
        }
    }
*/
    private fun handleError(message: String) {
        Log.w("Error", message)
        // Optionally, update UI state to reflect the error if needed.
        _categoryProducts.value = null  // Reset the category products in case of an error
        _allProducts.value = null        // Reset the all products in case of an error
      //  _favoriteAddOrDelete.value = null // Reset the favorite add or delete response
    }
}
