package com.mayuresh.countries.presentation.intent

sealed class CountryDetailsIntent{
    data class FetchCountryDetails(val data:String) : CountryDetailsIntent()
}
