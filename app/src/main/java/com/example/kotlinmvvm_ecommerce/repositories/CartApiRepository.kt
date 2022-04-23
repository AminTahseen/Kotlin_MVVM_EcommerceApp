package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.CartApiInterface
import com.example.kotlinmvvm_ecommerce.models.cart.Cart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartApiRepository @Inject constructor(private val cartApiInterface: CartApiInterface) {

    suspend fun getUserCartFlow(id:Int): Flow<Cart> {
        return flow<Cart>{
            val productResult=cartApiInterface.getCartByUserId(id)
            productResult.body()?.let {
                emit(it)
            }
        }
    }
}