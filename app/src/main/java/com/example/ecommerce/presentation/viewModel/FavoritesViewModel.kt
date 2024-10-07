package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddOrDeleteResponse
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

    private val _favoriteAddOrDelete = MutableStateFlow<AddOrDeleteResponse?>(null)
    val favoriteAddOrDelete: StateFlow<AddOrDeleteResponse?> get() = _favoriteAddOrDelete

    fun favoriteAddOrDelete(productId: Int,onError: (String)->Unit,onSuccess: (String)->Unit) {
        Log.d("FavoritesViewModel", "favoriteAddOrDelete called with productId: $productId")
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