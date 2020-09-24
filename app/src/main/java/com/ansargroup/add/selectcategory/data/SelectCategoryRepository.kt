package com.ansargroup.add.selectcategory.data

class SelectCategoryRepository(private val selectCategoryRemoteDataSource: ISelectCategoryRemoteDataSource) :
    ISelectCategoryRepository {

    override suspend fun getCategories(): CategoriesResponse {
        return selectCategoryRemoteDataSource.getCategories()
    }

}