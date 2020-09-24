package com.ansargroup.add.selectcategory.data

/**
 * Created by Attia Gamea on 9/24/20.
 */
class SelectCategoryRemoteDataSource(private val selectCategoryService: ISelectCategoryService) :
    ISelectCategoryRemoteDataSource {

    override suspend fun getCategories(): CategoriesResponse {
        return selectCategoryService.getCategories()
    }
}