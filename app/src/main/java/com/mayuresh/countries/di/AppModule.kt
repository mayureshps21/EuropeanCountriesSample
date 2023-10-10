package com.mayuresh.countries.di

import android.content.Context
import com.mayuresh.countries.presentation.util.NetworkHelper
import com.mayuresh.data.repository.CountryDetailsRepository
import com.mayuresh.data.repository.EuropeanCountriesListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNetWorkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}
