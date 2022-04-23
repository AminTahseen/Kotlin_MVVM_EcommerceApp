package com.example.kotlinmvvm_ecommerce.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.Product
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productsApiRepository: ProductsApiRepository):
    ViewModel() {

    //state flow
    private val productListStateFlow= MutableStateFlow(Product())
    val productsStateFlow: MutableStateFlow<Product> get() = productListStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            productsApiRepository.getProductsFlow().collect {
                productsStateFlow.value=it
            }
        }
    }
}