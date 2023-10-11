package com.mayuresh.detail

import com.mayuresh.data.model.CountryDetailsModel


data class CountryDetailsUiState(
    val country: CountryDetailsModel = CountryDetailsModel(),
    val isLoading: Boolean = false,
    val errorCode: Int = 0,
)
