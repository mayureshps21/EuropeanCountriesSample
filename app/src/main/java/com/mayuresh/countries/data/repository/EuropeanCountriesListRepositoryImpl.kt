package com.mayuresh.countries.data.repository

import com.mayuresh.countries.data.dto.CountryDto
import com.mayuresh.countries.data.service.ApiService
import com.mayuresh.countries.domain.repository.EuropeanCountriesListRepository
import retrofit2.Response
import javax.inject.Inject

class EuropeanCountriesListRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    EuropeanCountriesListRepository {
    override suspend fun getEuropeCountriesList(): Response<List<CountryDto>> {
        return apiService.getEuropeCountriesData()
    }
}
