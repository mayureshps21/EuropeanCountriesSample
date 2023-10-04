package com.mayuresh.countries.presentation.state

import com.mayuresh.countries.domain.model.CountryDetailsUiState

data class CountryDetailsState(
    val country: CountryDetailsUiState = CountryDetailsUiState(),
    val isLoading: Boolean = false,
    val errorCode: Int = 0
)