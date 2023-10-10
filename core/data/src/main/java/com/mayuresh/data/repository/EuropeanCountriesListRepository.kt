package com.mayuresh.data.repository

import com.mayuresh.data.dto.CountryDto
import retrofit2.Response

interface EuropeanCountriesListRepository {

    suspend fun getEuropeCountriesList(): Response<List<CountryDto>>
}
