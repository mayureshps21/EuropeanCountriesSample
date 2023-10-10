package com.mayuresh.domain.usecase

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.model.CountryListModel
import com.mayuresh.data.repository.EuropeanCountriesListRepository
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.mapper.EuropeanCountryListMapper
import com.mayuresh.domain.util.Response
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
