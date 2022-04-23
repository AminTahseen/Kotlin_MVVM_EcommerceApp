package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiInterface {

    @GET("/products")
    suspend fun getAllProducts():Response<Product>

}