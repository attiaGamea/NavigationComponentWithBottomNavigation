package com.ansargroup.add.selectcategory.data

interface ISelectCategoryRemoteDataSource {
    suspend fun getCategories(): CategoriesResponse
}