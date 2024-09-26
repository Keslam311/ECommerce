package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.GetAddresses
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAddressesViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _getAddressesResponse = MutableStateFlow<GetAddresses?>(null)
    val getAddressesResponse: StateFlow<GetAddresses?> = _getAddressesResponse

    init {
        getAddresses()
    }

     fun getAddresses() {
        viewModelScope.launch {
            try {
                val response = apiService.getAddresses()
                if (response.isSuccessful) {
                    _getAddressesResponse.value = response.body()
                    Log.d("GetAddressesViewModel", "Addresses Response: ${_getAddressesResponse.value}")
                } else {
                    Log.e("GetAddressesViewModel", "Error fetching addresses: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("GetAddressesViewModel", "Exception: ${e.message}", e)
            }
        }
    }

}
