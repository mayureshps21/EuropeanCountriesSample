package com.mayuresh.domain.mapper

/**
 * This interface is used if we want to map server response to desired response
 */
interface Mapper<F, T> {
    /**
     * This method has one parameter which is source and returns
     * the desired result
     */
    fun mapFrom(from: F): T
}
