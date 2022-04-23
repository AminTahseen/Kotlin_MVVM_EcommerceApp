package com.example.kotlinmvvm_ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.api.CategoryApiInterface
import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.di.ApiAppModule
import com.example.kotlinmvvm_ecommerce.models.category.Category
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
import com.example.kotlinmvvm_ecommerce.repositories.CategoryApiRepository
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository
import com.example.kotlinmvvm_ecommerce.viewmodels.category.CategoryViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.category.CategoryViewModelFactory

import com.example.kotlinmvvm_ecommerce.viewmodels.product.ProductViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.product.ProductViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinmvvm_ecommerce.adapters.CategoryAdapter
import com.example.kotlinmvvm_ecommerce.adapters.ProductAdapter
import com.example.kotlinmvvm_ecommerce.listeners.CategorySelectListener
import com.example.kotlinmvvm_ecommerce.utils.InternetManager
import java.util.*
import kotlin.collections.ArrayList


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
    private lateinit var progressBar:ProgressBar
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
                        getAllProducts()
                        getAllCategories()
                        categoryList.reverse()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.my_cart -> {
                val intent = Intent(MainActivity@this,CartActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.logout -> {
                finish()
                val intent = Intent(MainActivity@this,LoginActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> { // Note the block
                super.onOptionsItemSelected(item)
            }
        }
    }
    private fun linkXML(){
        categoryRecycler=findViewById(R.id.categoryRecycler)
        categoryRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            true)
        productRecycler=findViewById(R.id.productRecycler)
        productRecycler.layoutManager=LinearLayoutManager(this)
        progressBar=findViewById(R.id.progressBar)
    }
    private fun initUI(){
        productApiInterface=ApiAppModule.provideProductRetrofitInstance(ApiAppModule.provideBaseURL())
        productsApiRepository= ProductsApiRepository(productApiInterface)
        productViewModel=ViewModelProvider(this,
            ProductViewModelFactory(productsApiRepository)
        )[ProductViewModel::class.java]

        categoryApiInterface=ApiAppModule.provideCategoryRetrofitInstance(ApiAppModule.provideBaseURL())
        categoryApiRepository= CategoryApiRepository(categoryApiInterface)
        categoryViewModel=ViewModelProvider(this,
        CategoryViewModelFactory(categoryApiRepository)
        )[CategoryViewModel::class.java]
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
        Log.d("categorySelected",categoryName)
        if(categoryName.toUpperCase().equals("All".toUpperCase())){
            getAllProducts()
        }else {
            getProductByCategory(categoryName)
        }
    }
    private fun getAllCategories(){
        lifecycleScope.launch {
            progressBar.visibility= View.VISIBLE
            categoryViewModel.categoryStateFlow.collect { category: Category ->
                category.iterator().forEach { cat:String ->
                    Log.d("categoryItem", cat)
                    categoryList.add(cat)
                    runOnUiThread {
                        progressBar.visibility= View.GONE
                        setCategoryAdapter()
                    }
                }
            }
        }
        categoryList.add(0,"All")

    }
    private fun getAllProducts(){
        lifecycleScope.launch {
            productViewModel.productsStateFlow.collect {product->
                if(productList.size>0)
                    productList.clear()
                product.iterator().forEach { productItem ->
                    Log.d("productItem", productItem.title)
                    productList.add(productItem)
                    runOnUiThread {
                        setProductAdapter()
                    }
                }
            }
        }
    }
    private fun getProductByCategory(categoryName: String){
        lifecycleScope.launch {

            progressBar.visibility= View.VISIBLE
            categoryViewModel.getCategoryProducts(categoryName)
            categoryViewModel.productStateFlow.collect {
                if(productList.size>0)
                    productList.clear()
                it.iterator().forEach { productItem ->
                    Log.d("productCategoryItem",productItem.title)
                    productList.add(productItem)
                    runOnUiThread {
                        progressBar.visibility= View.GONE
                        setProductAdapter()
                    }
                }
            }
        }
    }
}