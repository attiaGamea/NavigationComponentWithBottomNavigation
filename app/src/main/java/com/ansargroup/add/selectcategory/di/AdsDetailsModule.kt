package com.ansargroup.add.selectcategory.di

import com.ansargroup.add.selectcategory.data.*
import com.ansargroup.add.selectcategory.presentation.SelectCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val selectCategoryModule = module {
    factory { get<Retrofit>().create(ISelectCategoryService::class.java) }
    factory<ISelectCategoryRemoteDataSource> { SelectCategoryRemoteDataSource(get()) }
    factory<ISelectCategoryRepository> { SelectCategoryRepository(get()) }
    viewModel { SelectCategoryViewModel(get()) }
}