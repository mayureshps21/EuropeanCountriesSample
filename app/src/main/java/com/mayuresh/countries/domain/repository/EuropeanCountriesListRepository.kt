package com.mayuresh.countries.domain.repository

import com.mayuresh.countries.data.dto.CountryDto
import retrofit2.Response

interface EuropeanCountriesListRepository {

    suspend fun getEuropeCountriesList(): Response<List<CountryDto>>
}
