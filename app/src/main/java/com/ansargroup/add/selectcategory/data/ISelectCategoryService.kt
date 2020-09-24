package com.ansargroup.add.selectcategory.data

import retrofit2.http.*

interface ISelectCategoryService{

    @GET("Category")
    suspend fun getCategories(): CategoriesResponse
}