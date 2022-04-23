package com.example.kotlinmvvm_ecommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem
import com.example.kotlinmvvm_ecommerce.models.user.UserItem
import com.example.kotlinmvvm_ecommerce.utils.PrefManager

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productName:TextView
    private lateinit var productDescription:TextView
    private lateinit var productPrice:TextView
    private lateinit var productCategory:TextView
    private lateinit var productImage:ImageView
    private lateinit var productRating:RatingBar
    private lateinit var productItem: ProductItem
    private lateinit var addToCartButton:Button
    private lateinit var quantityEdittext:EditText
    private lateinit var prefManager: PrefManager
    private lateinit var user:UserItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        linkXML()
        initUI()

        addToCartButton.setOnClickListener {
            addToCart()
        }
    }

    private fun linkXML(){
        productName=findViewById(R.id.productName)
        productCategory=findViewById(R.id.productCategory)
        productDescription=findViewById(R.id.productDescription)
        productPrice=findViewById(R.id.productPrice)
        productImage=findViewById(R.id.productImage)
        productRating=findViewById(R.id.rating)
        addToCartButton=findViewById(R.id.addToCart)
        quantityEdittext=findViewById(R.id.quantityEdittext)
    }
    private fun initUI(){
        getBundle()
        setDetails()
        prefManager=PrefManager(this@ProductDetailActivity)
        user=prefManager.getUserFromPref()

    }
    private fun getBundle(){
        productItem = intent.getParcelableExtra<ProductItem>("product_item")!!
        Log.d("productItem",productItem.toString())
    }

    private fun setDetails(){
        productName.text=productItem.title
        productCategory.text=productItem.category
        productPrice.text="$"+productItem.price.toString()
        productDescription.text=productItem.description
        Glide.with(ProductDetailActivity@this).load(productItem.image).into(productImage)
        productRating.rating= productItem.rating.rate.toFloat()
    }
    private fun addToCart(){

    }
}