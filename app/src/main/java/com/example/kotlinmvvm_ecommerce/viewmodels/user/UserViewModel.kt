package com.example.kotlinmvvm_ecommerce.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvm_ecommerce.models.user.User
import com.example.kotlinmvvm_ecommerce.repositories.UsersApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val usersApiRepository: UsersApiRepository):
    ViewModel()  {

    //state flow
    private val userListStateFlow= MutableStateFlow(User())
    val usersStateFlow: MutableStateFlow<User> get() = userListStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersApiRepository.getCategoriesFlow().collect {
                usersStateFlow.value=it
            }
        }
    }
}