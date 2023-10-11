package com.mayuresh.detail

sealed class CountryDetailsIntent {
    data class FetchCountryDetails(val data: String) : CountryDetailsIntent()
}
