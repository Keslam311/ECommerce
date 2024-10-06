package com.example.ecommerce.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.GetFavoritesResponse
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _favorites = MutableStateFlow<GetFavoritesResponse?>(null)
    val favorites: StateFlow<GetFavoritesResponse?> get() = _favorites

    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            val response = apiService.getFavorites()
            if (response.isSuccessful) {
                _favorites.value = response.body()
            }else{
                _favorites.value = null
            }
        }
    }

    fun favoriteAddOrDelete(productId: Int,onError: (String)->Unit,onSuccess: (String)->Unit) {
        viewModelScope.launch {
            val response = apiService.addOrDeleteFavorites(productId)
            if (response.isSuccessful) {
                _favoriteAddOrDelete.value = response.body()
                onSuccess(response.message())
                }else{
                _favoriteAddOrDelete.value = null
                onError(response.message())
            }
        }
    }
}