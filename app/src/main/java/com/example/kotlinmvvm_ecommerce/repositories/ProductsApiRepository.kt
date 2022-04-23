package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.models.cart.Cart
import com.example.kotlinmvvm_ecommerce.models.product.Product
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
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
    suspend fun getProductByIdFlow(id:Int): Flow<ProductItem> {
        return flow<ProductItem>{
            val productResult=productApiInterface.getProductById(id)
            productResult.body()?.let {
                emit(it)
            }
        }
    }
}