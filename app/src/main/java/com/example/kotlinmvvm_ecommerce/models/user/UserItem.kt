package com.example.kotlinmvvm_ecommerce.models.user



data class UserItem(
    val __v: Int,
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String
)