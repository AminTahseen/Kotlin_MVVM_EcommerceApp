package com.example.kotlinmvvm_ecommerce.viewmodels.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.category.Category
import com.example.kotlinmvvm_ecommerce.models.product.Product
import com.example.kotlinmvvm_ecommerce.repositories.CategoryApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryApiRepository: CategoryApiRepository):
    ViewModel() {

    //category flow
    private val categoryListStateFlow= MutableStateFlow(Category())
    val categoryStateFlow: MutableStateFlow<Category> get() = categoryListStateFlow

    //category products flow
    private val categoryProductStateFlow= MutableStateFlow(Product())
    val productStateFlow: MutableStateFlow<Product> get() = categoryProductStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoryApiRepository.getCategoriesFlow().collect {
                categoryStateFlow.value=it
                Log.d("insideThis",it.toString())
            }
        }
    }
   suspend fun getCategoryProducts(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            categoryApiRepository.getProductsFlow(id).collect {
                productStateFlow.value=it
            }
        }
    }
}