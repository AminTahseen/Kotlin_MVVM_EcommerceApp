package com.example.kotlinmvvm_ecommerce.models.cart

import com.example.kotlinmvvm_ecommerce.models.product.ProductItem

data class cartProductItem(
    val id: Int,
    val userId: Int,
    val products: ProductItem,
    val quantity: Int
)
