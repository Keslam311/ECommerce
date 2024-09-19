package com.example.ecommerce.data.network

import com.example.ecommerce.data.model.Categories
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.data.model.LoginResponse
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.SignUpRequest
import com.example.ecommerce.data.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body signUpRequest: SignUpRequest) : Response<SignUpResponse>

    @GET("categories")
    suspend fun getCategories() : Response<Categories>

    @GET("products")
    suspend fun getAllProducts() : Response<Products>
}