package com.example.kotlinmvvm_ecommerce.viewmodels.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_ecommerce.repositories.CartApiRepository

class CartViewModelFactory (private val cartApiRepository: CartApiRepository):
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(cartApiRepository) as T
    }
}