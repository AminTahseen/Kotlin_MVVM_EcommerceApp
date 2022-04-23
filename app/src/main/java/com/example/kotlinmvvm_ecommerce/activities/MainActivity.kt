package com.example.kotlinmvvm_ecommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.api.CategoryApiInterface
import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.di.ApiAppModule
import com.example.kotlinmvvm_ecommerce.models.Category
import com.example.kotlinmvvm_ecommerce.models.ProductItem
import com.example.kotlinmvvm_ecommerce.repositories.CategoryApiRepository
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository
import com.example.kotlinmvvm_ecommerce.viewmodels.CategoryViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.CategoryViewModelFactory

import com.example.kotlinmvvm_ecommerce.viewmodels.ProductViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.ProductViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinmvvm_ecommerce.adapters.CategoryAdapter
import com.example.kotlinmvvm_ecommerce.adapters.ProductAdapter
import com.example.kotlinmvvm_ecommerce.listeners.CategorySelectListener
import com.example.kotlinmvvm_ecommerce.utils.InternetManager
import com.google.android.material.snackbar.Snackbar


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),CategorySelectListener {
    private lateinit var productApiInterface: ProductApiInterface
    private lateinit var productsApiRepository: ProductsApiRepository
    private lateinit var productViewModel: ProductViewModel

    private lateinit var categoryApiInterface: CategoryApiInterface
    private lateinit var categoryApiRepository: CategoryApiRepository
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryList: ArrayList<String>
    private lateinit var productList:ArrayList<ProductItem>

    private lateinit var categoryRecycler:RecyclerView
    private lateinit var productRecycler:RecyclerView
    var categorySelectListener: CategorySelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linkXML()
        initUI()

        InternetManager.Network.isNetworkAvailableWithInternetAccess(this).observe(
            this, Observer {
                if (it != null) {
                    if (it) {
                        Log.d("checkNet","available here")
                        //Do your work here
                        lifecycleScope.launch {
                            productViewModel.productsStateFlow.collect {
                                it.iterator().forEach { productItem ->
                                    Log.d("productItem", productItem.title)
                                    productList.add(productItem)
                                    runOnUiThread {
                                        setProductAdapter()
                                    }
                                }
                            }
                        }
                        lifecycleScope.launch {
                            categoryViewModel.categoryStateFlow.collect { category: Category ->
                                category.iterator().forEach { cat:String ->
                                    Log.d("categoryItem", cat)
                                    categoryList.add(cat)
                                    runOnUiThread {
                                        setCategoryAdapter()
                                    }
                                }
                            }
                        }
                    } else {
                        //Network is not available do another work
                       Log.d("checkNet","here")
                    }
                }
            })
        categorySelectListener=this

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater:MenuInflater= menuInflater
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
    private fun linkXML(){
        categoryRecycler=findViewById(R.id.categoryRecycler)
        categoryRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            true)
        productRecycler=findViewById(R.id.productRecycler)
        productRecycler.layoutManager=LinearLayoutManager(this)
    }
    private fun initUI(){
        productApiInterface=ApiAppModule.provideProductRetrofitInstance(ApiAppModule.provideBaseURL())
        productsApiRepository= ProductsApiRepository(productApiInterface)
        productViewModel=ViewModelProvider(this,
            ProductViewModelFactory(productsApiRepository))[ProductViewModel::class.java]

        categoryApiInterface=ApiAppModule.provideCategoryRetrofitInstance(ApiAppModule.provideBaseURL())
        categoryApiRepository= CategoryApiRepository(categoryApiInterface)
        categoryViewModel=ViewModelProvider(this,
        CategoryViewModelFactory(categoryApiRepository))[CategoryViewModel::class.java]
        categoryList= ArrayList()
        productList= ArrayList()

    }

    private fun setCategoryAdapter(){
        val categoryAdapter=CategoryAdapter(categoryList,this,this@MainActivity)
        categoryRecycler.adapter=categoryAdapter
    }

    private fun setProductAdapter(){
        val productAdapter=ProductAdapter(productList,this@MainActivity)
        productRecycler.adapter=productAdapter
    }

    override fun selectCategory(categoryName: String) {
        Log.d("categorySelected",categoryName);
    }
}