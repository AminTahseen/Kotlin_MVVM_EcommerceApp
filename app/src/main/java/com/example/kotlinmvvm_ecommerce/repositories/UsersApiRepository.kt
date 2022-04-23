package com.example.kotlinmvvm_ecommerce.repositories

import com.example.kotlinmvvm_ecommerce.api.UserApiInterface
import com.example.kotlinmvvm_ecommerce.models.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersApiRepository @Inject constructor(private val userApiInterface: UserApiInterface) {

    suspend fun getCategoriesFlow(): Flow<User> {
        return flow<User>{
            val categoryResult=userApiInterface.getAllUsers()
            categoryResult.body()?.let {
                emit(it)
            }
        }
    }
}