package com.example.kotlinmvvm_ecommerce.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm_ecommerce.repositories.UsersApiRepository

class UserViewModelFactory (private val usersApiRepository: UsersApiRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(usersApiRepository) as T
    }
}