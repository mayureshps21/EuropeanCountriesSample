package com.mayuresh.data.repository

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class CountryDetailsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    CountryDetailsRepository {
    override suspend fun getCountryDetails(code: String): Response<List<CountryDto>> {
        return apiService.getSingleCountryDetails(code)
    }
}
