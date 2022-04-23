package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.category.Category
import com.example.kotlinmvvm_ecommerce.models.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiInterface {

    @GET("/products/categories")
    suspend fun getAllCategories():Response<Category>

    @GET("/products/category/{id}")
    suspend fun getProductsByCategory(@Path("id") id:String):Response<Product>
}