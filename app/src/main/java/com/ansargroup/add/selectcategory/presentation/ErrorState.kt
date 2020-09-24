package com.ansargroup.add.selectcategory.presentation

/**
 * Created by Attia Gamea on 9/3/19.
 */
sealed class ErrorState {
    object ConnectionError : ErrorState()
    data class GeneralError(val errorMessage: String) : ErrorState()
}