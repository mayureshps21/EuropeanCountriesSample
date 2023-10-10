package com.mayuresh.data.repository

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class EuropeanCountriesListRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    EuropeanCountriesListRepository {
    override suspend fun getEuropeCountriesList(): Response<List<CountryDto>> {
        return apiService.getEuropeCountriesData()
    }
}
