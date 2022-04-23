package com.example.kotlinmvvm_ecommerce.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.activities.ProductDetailActivity
import com.example.kotlinmvvm_ecommerce.models.product.ProductItem

class ProductAdapter(private val productList:List<ProductItem>, private val context: Context):
RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductAdapterViewHolder, position: Int) {
       val productItem=productList[position]
       holder.productName.text=productItem.title
        Glide.with(context).load(productItem.image).into(holder.productImage)
        holder.productPrice.text= "$"+productItem.price.toString()
        holder.productCategory.text=productItem.category

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("product_item",productItem)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    class ProductAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {
        val productName:TextView=itemView.findViewById(R.id.productName)
        val productImage:ImageView=itemView.findViewById(R.id.productImage)
        val productPrice:TextView=itemView.findViewById(R.id.productPrice)
        val productCategory:TextView=itemView.findViewById(R.id.productCategory)

    }
}