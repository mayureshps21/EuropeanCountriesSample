package com.mayuresh.countries.presentation.state

import com.mayuresh.data.model.CountryListModel


data class CountriesListUiState(
    val countries: List<CountryListModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorCode: Int = 0,
)
