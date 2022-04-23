package com.example.kotlinmvvm_ecommerce.models.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductItem(
    val category: String="",
    val description: String="",
    val id: Int=-1,
    val image: String="",
    val price: Double=0.0,
    val rating: Rating=Rating(0,0.0),
    val title: String=""
): Parcelable