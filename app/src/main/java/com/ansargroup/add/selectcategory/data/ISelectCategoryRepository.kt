package com.ansargroup.add.selectcategory.data

interface ISelectCategoryRepository {
    suspend fun getCategories(): CategoriesResponse
}