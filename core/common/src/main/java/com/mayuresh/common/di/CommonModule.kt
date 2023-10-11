package com.mayuresh.common.di

import android.content.Context
import com.mayuresh.common.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Singleton
    @Provides
    fun getNetWorkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}