package com.example.kotlinmvvm_ecommerce.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.listeners.CategorySelectListener

class CategoryAdapter(private val categoryList:List<String>,private val listener: CategorySelectListener,private val context:Context):
RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>(){

    private var row_index=-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryAdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val categoryItem = categoryList[position]
        holder.categoryTextView.text = categoryItem
        holder.itemView.setOnClickListener {
            row_index = position
            listener.selectCategory(categoryItem)
            notifyDataSetChanged()

        }
        if (row_index == position) {
            holder.categoryTextView.setTextColor(Color.parseColor("#1abc9c"))
        }else{
            holder.categoryTextView.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }


    class CategoryAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val categoryTextView:TextView=itemView.findViewById(R.id.categoryName)
    }

}