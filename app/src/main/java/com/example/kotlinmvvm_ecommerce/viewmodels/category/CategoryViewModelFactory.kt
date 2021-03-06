package com.example.kotlinmvvm_ecommerce.viewmodels.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_ecommerce.repositories.CategoryApiRepository

class CategoryViewModelFactory (private val categoryApiRepository: CategoryApiRepository):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryApiRepository) as T
    }
}