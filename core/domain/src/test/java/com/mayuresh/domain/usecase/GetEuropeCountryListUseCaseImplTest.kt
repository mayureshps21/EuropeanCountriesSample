package com.mayuresh.domain.usecase

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.repository.EuropeanCountriesListRepository
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.TestData
import com.mayuresh.domain.mapper.EuropeanCountryListMapper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetEuropeCountryListUseCaseImplTest {
    @MockK
    private lateinit var europeCountriesListRepository: EuropeanCountriesListRepository

    private lateinit var getEuropeCountryListUseCase: GetEuropeanCountriesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getEuropeCountryListUseCase = GetEuropeanCountriesUseCase(europeCountriesListRepository)
    }

    @Test
    fun `Should validate and pass countries response success test`() = runTest {
        // Given
        val response: Response<List<CountryDto>> = TestData.getCountriesResponseRetrofit(false)
        val result = EuropeanCountryListMapper().mapFrom(response.body() as List<CountryDto>)
        // When
        coEvery { europeCountriesListRepository.getEuropeCountriesList() } returns response
        val responseFlow = getEuropeCountryListUseCase()
        // Then
        responseFlow.collectLatest { data ->
            if (data is com.mayuresh.domain.util.Response.Success) {
                Assert.assertEquals("Finland", result.get(0).name)
            } else {
                Assert.assertTrue(false)
            }
        }
    }

    @Test
    fun `Should validate and pass countries response error test`() = runTest {
        // Given
        val response: Response<List<CountryDto>> = TestData.getCountriesResponseRetrofit(true)
        // When
        coEvery { europeCountriesListRepository.getEuropeCountriesList() } returns response
        val responseFlow = getEuropeCountryListUseCase()
        // Then
        responseFlow.collectLatest { data ->
            if (data is com.mayuresh.domain.util.Response.Error) {
                Assert.assertEquals(AppConstants.API_RESPONSE_ERROR, data.code)
            } else {
                Assert.assertTrue(false)
            }
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}