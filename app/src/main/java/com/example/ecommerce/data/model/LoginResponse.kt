package com.example.ecommerce.data.model

import com.example.ecommerce.data.model.DataXX

data class LoginResponse(
    val `data`: DataXX,
    val message: String,
    val status: Boolean
)