package com.example.kotlinmvvm_ecommerce.viewmodels.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.product.Product
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
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

    //product item state flow
    private val productItemStateFlow= MutableStateFlow(ProductItem())
    val productStateFlow: MutableStateFlow<ProductItem> get() = productItemStateFlow


    init {
        viewModelScope.launch(Dispatchers.IO) {
            productsApiRepository.getProductsFlow().collect {
                productsStateFlow.value=it
            }
        }
    }

    suspend fun getProductById(id:Int){
        viewModelScope.launch {
            productsApiRepository.getProductByIdFlow(id).collect {
                productStateFlow.value=it
            }
        }
    }
}