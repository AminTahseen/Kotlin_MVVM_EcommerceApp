package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.Category
import com.example.kotlinmvvm_ecommerce.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApiInterface {

    @GET("/products/categories")
    suspend fun getAllCategories():Response<Category>

}