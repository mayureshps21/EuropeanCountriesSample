package com.mayuresh.countries.domain.usecase

import android.content.Context
import com.mayuresh.countries.R
import com.mayuresh.countries.data.mapper.CountryDetailsMapper
import com.mayuresh.countries.data.dto.CountryDto
import com.mayuresh.countries.data.util.AppConstants
import com.mayuresh.countries.domain.util.Response
import com.mayuresh.countries.domain.model.CountryDetailsModel
import com.mayuresh.countries.domain.repository.CountryDetailsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesDetailsUseCase @Inject constructor(
    private val countryDetailsRepository: CountryDetailsRepository,
    @ApplicationContext val context: Context,
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
                            message = context.getString(R.string.something_went_wrong),
                        ),
                    )
                }
            } else {
                emit(Response.Error(code = AppConstants.API_RESPONSE_ERROR, message = response.message()))
            }
        }
}
