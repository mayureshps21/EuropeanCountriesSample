package com.mayuresh.countries.presentation.state

import com.mayuresh.countries.domain.model.CountryListUiState

data class CountriesListState(
    val countries: List<CountryListUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorCode: Int = 0
)
