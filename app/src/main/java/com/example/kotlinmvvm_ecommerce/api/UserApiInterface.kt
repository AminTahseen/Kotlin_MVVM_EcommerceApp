package com.example.kotlinmvvm_ecommerce.api

import com.example.kotlinmvvm_ecommerce.models.user.User
import retrofit2.Response
import retrofit2.http.GET

interface UserApiInterface {

    @GET("/users")
    suspend fun getAllUsers(): Response<User>


}