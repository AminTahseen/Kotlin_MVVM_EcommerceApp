package com.example.kotlinmvvm_ecommerce.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.Category
import com.example.kotlinmvvm_ecommerce.models.Product
import com.example.kotlinmvvm_ecommerce.repositories.CategoryApiRepository
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryApiRepository: CategoryApiRepository):
    ViewModel() {

    //state flow
    private val categoryListStateFlow= MutableStateFlow(Category())
    val categoryStateFlow: MutableStateFlow<Category> get() = categoryListStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoryApiRepository.getCategoriesFlow().collect {
                categoryStateFlow.value=it
                Log.d("insideThis",it.toString())
            }
        }
    }
}