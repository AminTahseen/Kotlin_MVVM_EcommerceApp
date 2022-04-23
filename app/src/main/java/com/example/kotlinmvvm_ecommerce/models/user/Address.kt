package com.example.kotlinmvvm_ecommerce.models.user


data class Address(
    val city: String,
    val geolocation: Geolocation,
    val number: Int,
    val street: String,
    val zipcode: String
)