package com.mayuresh.data.di

import com.mayuresh.data.repository.CountryDetailsRepository
import com.mayuresh.data.repository.CountryDetailsRepositoryImpl
import com.mayuresh.data.repository.EuropeanCountriesListRepository
import com.mayuresh.data.repository.EuropeanCountriesListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCountryListRepository(
        europeCountriesListRepositoryImpl: EuropeanCountriesListRepositoryImpl,
    ): EuropeanCountriesListRepository

    @Binds
    abstract fun bindCountryDetailsRepository(
        countryDetailsRepositoryImpl: CountryDetailsRepositoryImpl,
    ): CountryDetailsRepository
}
