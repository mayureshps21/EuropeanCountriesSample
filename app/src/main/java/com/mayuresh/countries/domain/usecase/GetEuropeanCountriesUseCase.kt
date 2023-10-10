package com.mayuresh.countries.domain.usecase

import com.mayuresh.countries.data.mapper.EuropeanCountryListMapper
import com.mayuresh.countries.data.dto.CountryDto
import com.mayuresh.countries.data.util.AppConstants
import com.mayuresh.countries.domain.util.Response
import com.mayuresh.countries.domain.model.CountryListModel
import com.mayuresh.countries.domain.repository.EuropeanCountriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEuropeanCountriesUseCase @Inject constructor(private val europeCountriesListRepository: EuropeanCountriesListRepository) {
    suspend operator fun invoke(): Flow<Response<List<CountryListModel>>> =
        flow {
            val response = europeCountriesListRepository.getEuropeCountriesList()
            if (response.isSuccessful) {
                val countriesList = response.body() as List<CountryDto>
                val result = EuropeanCountryListMapper().mapFrom(countriesList)
                emit(Response.Success(result))
            } else {
                emit(Response.Error(code = AppConstants.API_RESPONSE_ERROR, message = response.message()))
            }
        }
}
