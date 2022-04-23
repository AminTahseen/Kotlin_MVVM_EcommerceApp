package com.example.kotlinmvvm_ecommerce.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository

class ProductViewModelFactory (private val productsApiRepository: ProductsApiRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(productsApiRepository) as T
    }
}