package com.ansargroup.add.selectcategory.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansargroup.base.data.exception.APIException
import com.ansargroup.base.data.remote.RemoteConstants
import com.ansargroup.add.selectcategory.data.Category
import com.ansargroup.add.selectcategory.data.ISelectCategoryRepository
import kotlinx.coroutines.launch

/**
 * Created by Attia Gamea on 9/24/20.
 */
class SelectCategoryViewModel(private val selectCategoryRepository: ISelectCategoryRepository) :
    ViewModel() {

    private val _loadingStatusLiveData = MutableLiveData<Boolean>()
    val loadingStatusLiveData: LiveData<Boolean> = _loadingStatusLiveData

    private val _contentStatusLiveData = MutableLiveData<Boolean>()
    val contentStatusData: LiveData<Boolean> = _contentStatusLiveData

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesData: LiveData<List<Category>> = _categoriesLiveData

    private val _errorLiveData = MutableLiveData<ErrorState>()
    val errorData: LiveData<ErrorState> = _errorLiveData

    fun getCategories() {
        viewModelScope.launch {
            kotlin.runCatching {
                _loadingStatusLiveData.value = true
                _contentStatusLiveData.value = false
                selectCategoryRepository.getCategories()
            }.onSuccess { categoriesData ->
                _loadingStatusLiveData.value = false
                _contentStatusLiveData.value = true
                _categoriesLiveData.value = categoriesData.categories
            }.onFailure { throwable ->
                _loadingStatusLiveData.value = false
                _contentStatusLiveData.value = false
                if (throwable is APIException) {
                    when (throwable.code) {
                        RemoteConstants.LOST_CONNECTION_STATUS_CODE, RemoteConstants.TIME_OUT_STATUS_CODE -> {
                            _errorLiveData.value = ErrorState.ConnectionError
                        }
                        else -> {
                            _errorLiveData.value = ErrorState.GeneralError(throwable.errorMessage)
                        }
                    }
                } else {
                    _errorLiveData.value = ErrorState.GeneralError("")
                }
            }
        }
    }
}