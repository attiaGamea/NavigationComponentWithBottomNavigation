package com.eigthytech.eighty.base.usecases

/**
 * Created by Attia Gamea on 16/06/19.
 */
interface UseCase<in P, out R> {
    fun execute(param: P?): R
}