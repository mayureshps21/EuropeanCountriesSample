package com.mayuresh.countries.data.repository

import com.mayuresh.countries.TestData
import com.mayuresh.countries.data.service.ApiService
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CountryDetailsRepositoryImplTest {

    @MockK
    lateinit var apiService: ApiService

    private lateinit var countryDetailsRepositoryImpl: CountryDetailsRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        countryDetailsRepositoryImpl = CountryDetailsRepositoryImpl(apiService)
    }

    @Test
    fun `Should validate api service call`() = runTest {
        // Given
        val apiResponse = Response.success(TestData.getCountriesData())

        // When
        coEvery {
            apiService.getSingleCountryDetails("190")
        } returns apiResponse

        countryDetailsRepositoryImpl.getCountryDetails("190")
        // Then
        coVerify(atLeast = 1) { apiService.getSingleCountryDetails("190") }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}