package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.CategoryApiInterface
import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.models.Category
import com.example.kotlinmvvm_ecommerce.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryApiRepository @Inject constructor(private val categoryApiInterface: CategoryApiInterface) {

    suspend fun getCategoriesFlow():Flow<Category>{
        return flow<Category>{
            val categoryResult=categoryApiInterface.getAllCategories()
            categoryResult.body()?.let {
                emit(it)
            }
        }
    }
}