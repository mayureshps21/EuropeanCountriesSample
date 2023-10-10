package com.mayuresh.countries.domain.repository

import com.mayuresh.countries.data.dto.CountryDto
import retrofit2.Response

interface CountryDetailsRepository {
    suspend fun getCountryDetails(code: String): Response<List<CountryDto>>
}
