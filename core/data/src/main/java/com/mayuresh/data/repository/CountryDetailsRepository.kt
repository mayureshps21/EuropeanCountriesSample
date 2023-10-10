package com.mayuresh.data.repository

import com.mayuresh.data.dto.CountryDto
import retrofit2.Response

interface CountryDetailsRepository {
    suspend fun getCountryDetails(code: String): Response<List<CountryDto>>
}
