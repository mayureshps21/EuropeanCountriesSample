package com.mayuresh.domain.usecase

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.model.CountryDetailsModel
import com.mayuresh.data.repository.CountryDetailsRepository
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.mapper.CountryDetailsMapper
import com.mayuresh.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesDetailsUseCase @Inject constructor(
    private val countryDetailsRepository: CountryDetailsRepository
) {
    suspend operator fun invoke(code: String): Flow<Response<CountryDetailsModel>> =
        flow {
            val response = countryDetailsRepository.getCountryDetails(code)
            if (response.isSuccessful) {
                val countryList = response.body() as List<CountryDto>
                if (countryList.isNotEmpty()) {
                    val result = CountryDetailsMapper().mapFrom(countryList.get(0))
                    emit(Response.Success(result))
                } else {
                    emit(
                        Response.Error(
                            code = AppConstants.API_RESPONSE_ERROR,
                            message = response.message(),
                        ),
                    )
                }
            } else {
                emit(
                    Response.Error(
                        code = AppConstants.API_RESPONSE_ERROR,
                        message = response.message()
                    )
                )
            }
        }
}
