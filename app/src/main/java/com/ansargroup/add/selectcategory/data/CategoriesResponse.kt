package com.ansargroup.add.selectcategory.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Attia Gamea on 9/24/20.
 */
class CategoriesResponse(
    @SerializedName("result") val categories: List<Category>,
    @SerializedName("errorMessage") val errorMessage: String
)