package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.CategoryApiInterface
import com.example.kotlinmvvm_ecommerce.models.category.Category
import com.example.kotlinmvvm_ecommerce.models.product.Product
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

    suspend fun getProductsFlow(id:String):Flow<Product>{
        return flow<Product>{
            val productResult=categoryApiInterface.getProductsByCategory(id)
            productResult.body()?.let {
                emit(it)
            }
        }
    }
}