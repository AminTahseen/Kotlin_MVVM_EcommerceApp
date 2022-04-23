package com.example.kotlinmvvm_ecommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.models.cart.cartProductItem

class CartAdapter(private val productList:List<cartProductItem>, private val context: Context):
    RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartAdapterViewHolder, position: Int) {
        var item=productList[position]
        holder.productQTY.text="Total QTY - "+item.quantity.toString()
        holder.productName.text=item.products.title
        holder.productPrice.text="$"+item.products.price
        Glide.with(context).load(item.products.image).into(holder.productImage)
    }

    override fun getItemCount(): Int {
       return productList.size
    }
    class CartAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val productName: TextView =itemView.findViewById(R.id.productName)
        val productImage: ImageView =itemView.findViewById(R.id.productImage)
        val productPrice: TextView =itemView.findViewById(R.id.productPrice)
        val productQTY: TextView =itemView.findViewById(R.id.productQTY)
    }
}