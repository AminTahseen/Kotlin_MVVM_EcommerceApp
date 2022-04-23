package com.example.kotlinmvvm_ecommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.adapters.CartAdapter
import com.example.kotlinmvvm_ecommerce.adapters.ProductAdapter
import com.example.kotlinmvvm_ecommerce.api.CartApiInterface
import com.example.kotlinmvvm_ecommerce.api.ProductApiInterface
import com.example.kotlinmvvm_ecommerce.di.ApiAppModule
import com.example.kotlinmvvm_ecommerce.models.cart.CartItem
import com.example.kotlinmvvm_ecommerce.models.cart.cartProductItem
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
import com.example.kotlinmvvm_ecommerce.models.user.User
import com.example.kotlinmvvm_ecommerce.models.user.UserItem
import com.example.kotlinmvvm_ecommerce.repositories.CartApiRepository
import com.example.kotlinmvvm_ecommerce.repositories.ProductsApiRepository
import com.example.kotlinmvvm_ecommerce.utils.PrefManager
import com.example.kotlinmvvm_ecommerce.viewmodels.cart.CartViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.cart.CartViewModelFactory
import com.example.kotlinmvvm_ecommerce.viewmodels.product.ProductViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.product.ProductViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {
    private lateinit var cartApiInterface: CartApiInterface
    private lateinit var cartApiRepository: CartApiRepository
    private lateinit var cartViewModel: CartViewModel

    private lateinit var productApiInterface: ProductApiInterface
    private lateinit var productsApiRepository: ProductsApiRepository
    private lateinit var productViewModel: ProductViewModel

    private lateinit var cartRecycler:RecyclerView
    private lateinit var prefManager: PrefManager
    private lateinit var user:UserItem
    private lateinit var cartItemList: ArrayList<cartProductItem>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        linkXML()
        initUI()
        getCartItems()

    }

    private fun getCartItems(){
        lifecycleScope.launch {
            progressBar.visibility= View.VISIBLE

            Log.d("user",user.toString())
            cartViewModel.getUserCart(user.id)
            cartViewModel.cartStateFlow.collect {cart->
                cart.iterator().forEach { cartItem ->

                    Log.d("UserCart",cartItem.toString())
                    cartItem.products.iterator().forEach { product ->
                        getCartProducts(cartItem.id,product.productId,product.quantity)
                    }
                }
            }
        }
    }
    private fun getCartProducts(cartId:Int,id:Int,quantity:Int){


        lifecycleScope.launch {
            if (cartItemList.size>0)
                cartItemList.clear()
            productViewModel.getProductById(id)
            productViewModel.productStateFlow.collect {value: ProductItem ->
                var cartItem=cartProductItem(cartId,user.id,value,quantity)
                Log.d("userCartItem",cartItem.toString()+"\n")
                if(!cartItemList.contains(cartItem) && cartItem.id!=-1)
                    cartItemList.add(cartItem)

                setCartAdapter()
                progressBar.visibility= View.GONE
            }
        }
    }
    private fun setCartAdapter(){
        val cartAdapter= CartAdapter(cartItemList,this@CartActivity)
        cartRecycler.adapter=cartAdapter
    }
    private fun linkXML(){
        cartRecycler=findViewById(R.id.cartRecycler)
        cartRecycler.layoutManager= LinearLayoutManager(this)
        progressBar=findViewById(R.id.progressBar)

    }
    private fun initUI(){
        cartApiInterface=ApiAppModule.provideCartRetrofitInstance(ApiAppModule.provideBaseURL())
        cartApiRepository=CartApiRepository(cartApiInterface)
        cartViewModel= ViewModelProvider(this,
            CartViewModelFactory(cartApiRepository)
        )[CartViewModel::class.java]

        productApiInterface=ApiAppModule.provideProductRetrofitInstance(ApiAppModule.provideBaseURL())
        productsApiRepository= ProductsApiRepository(productApiInterface)
        productViewModel=ViewModelProvider(this,
            ProductViewModelFactory(productsApiRepository)
        )[ProductViewModel::class.java]

        prefManager=PrefManager(this@CartActivity)
        user=prefManager.getUserFromPref()
        cartItemList= ArrayList()
    }
}