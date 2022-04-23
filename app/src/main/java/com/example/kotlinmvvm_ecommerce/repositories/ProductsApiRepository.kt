package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsApiRepository @Inject constructor(private val productApiInterface: ProductApiInterface) {

    suspend fun getProductsFlow():Flow<Product>{
        return flow<Product>{
            val productResult=productApiInterface.getAllProducts()

            productResult.body()?.let {
                emit(it)
            }
        }
    }
}