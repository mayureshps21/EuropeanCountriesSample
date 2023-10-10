package com.mayuresh.countries.di

import android.content.Context
import com.mayuresh.countries.domain.repository.CountryDetailsRepository
import com.mayuresh.countries.domain.repository.EuropeanCountriesListRepository
import com.mayuresh.countries.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.countries.domain.usecase.GetEuropeanCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGetEuropeanCountriesUseCase(europeanCountriesListRepository: EuropeanCountriesListRepository): GetEuropeanCountriesUseCase {
        return GetEuropeanCountriesUseCase(europeanCountriesListRepository)
    }

    @Provides
    fun provideGetCountryInformationUseCase(
        countryDetailsRepository: CountryDetailsRepository,
        @ApplicationContext context: Context,
    ): GetCountriesDetailsUseCase {
        return GetCountriesDetailsUseCase(
            countryDetailsRepository = countryDetailsRepository,
            context = context,
        )
    }
}
