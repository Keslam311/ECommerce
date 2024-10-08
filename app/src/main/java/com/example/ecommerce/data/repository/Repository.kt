package com.example.ecommerce.data.repository

import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ApiService) {
    private var cachedProducts: Products? = null

    fun getCachedProducts(): Products? {
        return cachedProducts
    }

    suspend fun fetchAllProducts(): Products? {
        // Call your API and return products
        val products = api.getAllProduct()
        cachedProducts = products.body() // Cache the data
        return products.body()
    }

    fun cacheProducts(products: Products) {
        cachedProducts = products // Store the products in cache
    }
}
