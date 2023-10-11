package com.mayuresh.domain.di

import com.mayuresh.data.repository.CountryDetailsRepository
import com.mayuresh.data.repository.EuropeanCountriesListRepository
import com.mayuresh.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.domain.usecase.GetEuropeanCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetEuropeanCountriesUseCase(europeanCountriesListRepository: EuropeanCountriesListRepository): GetEuropeanCountriesUseCase {
        return GetEuropeanCountriesUseCase(europeanCountriesListRepository)
    }

    @Provides
    fun provideGetCountryInformationUseCase(
        countryDetailsRepository: CountryDetailsRepository
    ): GetCountriesDetailsUseCase {
        return GetCountriesDetailsUseCase(
            countryDetailsRepository = countryDetailsRepository
        )
    }
}