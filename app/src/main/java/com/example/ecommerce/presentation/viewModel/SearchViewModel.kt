package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.SearchRequest
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _productSearch = MutableStateFlow<Products?>(null)
    val productSearch : StateFlow<Products?> get() = _productSearch

    init {
        getProductsSearch()
    }

    fun getProductsSearch(text: String=""){
        viewModelScope.launch {
            val response = apiService.getSearch(SearchRequest(text))
            if(response.isSuccessful){
                _productSearch.value = response.body()
            }else{
                _productSearch.value = null
            }
        }
    }
}

