package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.cart.Cart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiInterface {
    @GET("/carts/user/{id}")
    suspend fun getCartByUserId(@Path("id") id:Int): Response<Cart>

}