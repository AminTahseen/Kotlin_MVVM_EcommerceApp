package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.product.Product
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiInterface {

    @GET("/products")
    suspend fun getAllProducts():Response<Product>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id:Int):Response<ProductItem>
}