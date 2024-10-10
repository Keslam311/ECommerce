package com.example.ecommerce.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.AddressesResponse
import com.example.ecommerce.data.model.UpdateAddressesRequest
import com.example.ecommerce.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateAddressesViewModel @Inject constructor(
    private val apiService: ApiService,
): ViewModel() {

    private val _updateAddressesResponse = MutableStateFlow<AddressesResponse?>(null)
    val updateAddressesResponse: StateFlow<AddressesResponse?> = _updateAddressesResponse.asStateFlow()


    fun updateAddresses(
        id: Int,
        name: String,
        city: String,
        region: String,
        details: String,
        notes: String,
        latitude: Double,
        longitude: Double,
        onSuccessful: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = apiService.getUpdateAddresses((id),
                            updateAddressesRequest = UpdateAddressesRequest(
                            name = name,
                            city = city,
                            region = region,
                            details = details,
                                notes = notes,
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                if (response.isSuccessful){
                    _updateAddressesResponse.value = response.body()
                    onSuccessful()
                }
                else{
                    onError("Error: ${response.message()}")
                    Log.e("UpdateAddressesViewModel", "Error: ${response.code()}")
                }
            }
            catch (e: Exception){
                Log.e("UpdateAddressesViewModel", "Exception: ${e.message}", e)
            }
        }
    }
}