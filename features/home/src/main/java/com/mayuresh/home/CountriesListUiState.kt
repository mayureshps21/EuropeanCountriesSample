package com.mayuresh.home

import com.mayuresh.data.model.CountryListModel


data class CountriesListUiState(
    val countries: List<CountryListModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorCode: Int = 0,
)
