package com.mayuresh.countries.domain.usecase

import android.content.Context
import com.mayuresh.countries.TestData
import com.mayuresh.countries.data.mapper.CountryDetailsMapper
import com.mayuresh.countries.data.model.CountryModel
import com.mayuresh.countries.domain.repository.CountryDetailsRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetCountryInformationUseCaseTest {
    @MockK
    private lateinit var countryDetailsRepository: CountryDetailsRepository

    @RelaxedMockK
    private lateinit var context: Context

    private lateinit var getCountriesDetailsUseCase: GetCountriesDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCountriesDetailsUseCase =
            GetCountriesDetailsUseCase(countryDetailsRepository, context)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Should validate and pass countries response success test`() = runTest {
        val response: Response<List<CountryModel>> = TestData.getFinlandCountryResponseRetrofit(false)
        val countryList = response.body() as List<CountryModel>
        val result = CountryDetailsMapper().mapFrom(countryList.get(0))
        // Given
        coEvery { countryDetailsRepository.getCountryDetails("419") } returns response
        // When
        val responseFlow = getCountriesDetailsUseCase.invoke("419")
        // Then
        responseFlow.collectLatest { data ->
            when (data) {
                is com.mayuresh.countries.data.util.Response.Success -> {
                    Assert.assertEquals("Finland", result.name)
                }

                else -> {
                    Assert.assertEquals(true, false)
                }
            }
        }
    }

    @Test
    fun `Should validate and pass country response error test`() = runTest {
        val response: Response<List<CountryModel>> = TestData.getFinlandCountryResponseRetrofit(true)
        // Given
        coEvery { countryDetailsRepository.getCountryDetails("419") } returns response
        // When
        val responseFlow = getCountriesDetailsUseCase.invoke("419")
        // Then
        responseFlow.collectLatest { data ->
            when (data) {
                is com.mayuresh.countries.data.util.Response.Error -> {
                    Assert.assertEquals(400, data.code)
                }
                else -> {
                    Assert.assertEquals(true, false)
                }
            }
        }
    }

    @Test
    fun `Should validate and pass country response empty records error test`() = runTest {
        val response: Response<List<CountryModel>> = Response.success(mutableListOf())
        // Given
        coEvery { countryDetailsRepository.getCountryDetails("419") } returns response
        // When
        val responseFlow = getCountriesDetailsUseCase.invoke("419")
        // Then
        responseFlow.collectLatest { data ->
            when (data) {
                is com.mayuresh.countries.data.util.Response.Error -> {
                    Assert.assertEquals(500, data.code)
                }
                else -> {
                    Assert.assertEquals(true, false)
                }
            }
        }
    }
}