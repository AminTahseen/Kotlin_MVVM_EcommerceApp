package com.example.kotlinmvvm_ecommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_ecommerce.R

class CategoryAdapter(private val categoryList:List<String>,private val context:Context):
RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        val categoryItem= categoryList[position]
        holder.categoryTextView.text=categoryItem
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    class CategoryAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val categoryTextView:TextView=itemView.findViewById(R.id.categoryName)
    }

}