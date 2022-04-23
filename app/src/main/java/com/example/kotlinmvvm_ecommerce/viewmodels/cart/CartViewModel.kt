package com.example.kotlinmvvm_ecommerce.viewmodels.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.cart.Cart
import com.example.kotlinmvvm_ecommerce.repositories.CartApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel@Inject constructor(private val cartApiRepository: CartApiRepository):
    ViewModel() {

    //cart state flow
    private val cartListStateFlow= MutableStateFlow(Cart())
    val cartStateFlow: MutableStateFlow<Cart> get() = cartListStateFlow

    suspend fun getUserCart(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
           cartApiRepository.getUserCartFlow(id).collect {
               cartStateFlow.value=it
           }
        }
    }
}