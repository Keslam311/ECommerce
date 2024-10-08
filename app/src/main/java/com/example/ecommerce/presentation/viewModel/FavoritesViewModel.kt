/*
package com.example.ecommerce.presentation.viewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.GetFavorites
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _favorites = MutableStateFlow<GetFavorites?>(null)
    val favorites: StateFlow<GetFavorites?> get() = _favorites


    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts


    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete


    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            try {
                val response = apiService.getFavorites()
                if (response.isSuccessful) {
                    _favorites.value = response.body()
                } else {
                    _favorites.value = null
                }
            } catch (ex: Exception) {
                _favorites.value = null
            }
        }
    }

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

    private fun handleError(message: String) {
        Log.w("Error", message)
        // Optionally, update UI state to reflect the error if needed.
        _allProducts.value = null        // Reset the all products in case of an error
        _favoriteAddOrDelete.value = null // Reset the favorite add or delete response
    }
}
 */
package com.example.ecommerce.presentation.viewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.GetFavorites
import com.example.ecommerce.data.model.Product
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _favorites = MutableStateFlow<GetFavorites?>(null)
    val favorites: StateFlow<GetFavorites?> get() = _favorites

    private val _allProducts = MutableStateFlow<Products?>(null)
    val allProducts: StateFlow<Products?> get() = _allProducts


    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getFavorites()
                if (response.isSuccessful) {
                    _favorites.value = response.body()
                } else {
                    _favorites.value = null
                    handleError("Failed to get favorites: ${response.message()}")
                }
            } catch (ex: Exception) {
                _favorites.value = null
                handleError("Exception occurred while fetching favorites: ${ex.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
/*
    fun favoriteAddOrDelete(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.addOrDeleteFavorites(AddOrDeleteFavoriteRequest(product_id = productId))
                if (response.isSuccessful) {
                    _favoriteAddOrDelete.value = response.body()
                    getFavorites()  // Refresh the favorites list after updating
                    getAllProduct()  // Refresh the products list as well
                    onSuccess("Favorite updated successfully.")
                } else {
                    _favoriteAddOrDelete.value = null
                    handleError("Failed to add/delete favorite: ${response.message()}")
                    onError("Failed to add or delete favorite.")
                }
            } catch (ex: Exception) {
                _favoriteAddOrDelete.value = null
                handleError("Exception occurred: ${ex.localizedMessage}")
                onError("Exception: ${ex.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
 */

    fun favoriteAddOrDelete(
        productId: Int,
        onError: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = AddOrDeleteFavoriteRequest(product_id = productId)
                val response = apiService.addOrDeleteFavorites(request)

                if (response.isSuccessful) {
                    _favoriteAddOrDelete.value = response.body()
                    //Refresh the favorites list after updating
                    getFavorites()
                    //getAllProduct()
                    onSuccess("Favorite updated successfully.")
                } else {
                    _favoriteAddOrDelete.value = null
                    handleError("Failed to add/delete favorite: ${response.message()}")
                    onError("Failed to add or delete favorite.")
                }
            } catch (ex: Exception) {
                _favoriteAddOrDelete.value = null
                handleError("Exception occurred: ${ex.localizedMessage}")
                onError("Exception: ${ex.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

/*
//
//    fun getAllProduct() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val response = apiService.getAllProduct()
//                if (response.isSuccessful) {
//                    _allProducts.value = response.body()
//                } else {
//                    _allProducts.value = null
//                    handleError("Failed to fetch products: ${response.code()}")
//                }
//            } catch (ex: Exception) {
//                _allProducts.value = null
//                handleError("Exception occurred while fetching products: ${ex.localizedMessage}")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
*/
    private fun handleError(message: String) {
        Log.e("FavoritesViewModel", message)
        _errorMessage.value = message
    }
}
