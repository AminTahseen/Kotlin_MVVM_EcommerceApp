package com.example.kotlinmvvm_ecommerce.di

import com.example.kotlinmvvm_ecommerce.api.CategoryApiInterface
import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiAppModule {
    @Provides
    fun provideBaseURL()=Constants.MAIN_BASE_URL

    @Provides
    @Singleton
    fun provideProductRetrofitInstance(BASE_URL:String):ProductApiInterface=
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiInterface::class.java)

    @Provides
    @Singleton
    fun provideCategoryRetrofitInstance(BASE_URL:String):CategoryApiInterface=
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApiInterface::class.java)
}