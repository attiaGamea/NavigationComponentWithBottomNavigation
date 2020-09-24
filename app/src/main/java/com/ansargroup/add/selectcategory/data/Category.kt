package com.ansargroup.add.selectcategory.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Attia Gamea on 9/24/20.
 */
class Category(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("subCategories") val subCategories: List<Category>?
)