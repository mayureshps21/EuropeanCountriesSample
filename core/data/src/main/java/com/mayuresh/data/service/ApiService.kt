package com.mayuresh.data.service

import com.mayuresh.data.dto.CountryDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * This API call is for get all european country list
     */
    @GET("/v3.1/region/europe")
    suspend fun getEuropeCountriesData(): Response<List<CountryDto>>

    /**
     * This api call is used to get particular country details
     * @param code
     */
    @GET("/v3.1/alpha/{code}")
    suspend fun getSingleCountryDetails(
        @Path("code") code: String,
    ): Response<List<CountryDto>>
}
