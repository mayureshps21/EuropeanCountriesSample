package com.mayuresh.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.common.util.NetworkHelper
import com.mayuresh.data.model.CountryDetailsModel
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.domain.util.Response
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CountryDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getCountriesDetailsUseCase: GetCountriesDetailsUseCase

    @MockK
    private lateinit var networkHelper: NetworkHelper

    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var viewModel: CountryDetailsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        networkHelper = mockk(NetworkHelper::class.java.name, true)
        viewModel = CountryDetailsViewModel(
            getCountriesDetails = getCountriesDetailsUseCase,
            networkHelper = networkHelper,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `countryDetailsViewModel should update state with expected data when fetching country details with success and network available`() =
        runTest {
            //Given
            val isNetworkAvailable = true
            val country = CountryDetailsModel("Florida")
            val responseFlow: Flow<Response<CountryDetailsModel>> = flow {
                emit(Response.Success(country))
            }
            //When
            every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
            coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow
            viewModel.fetchCountryDetails("190")
            //Then
            responseFlow.collectLatest {
                Assert.assertEquals("Florida", viewModel.state.value.country.name)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `countryDetailsViewModel should update state with error code when fetching country details with error`() =
        runTest {
            //Given
            val isNetworkAvailable = true
            val responseFlow: Flow<Response<CountryDetailsModel>> = flow {
                emit(Response.Error(code = AppConstants.API_RESPONSE_ERROR, "Test"))
            }
            //When
            every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
            coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow
            viewModel.fetchCountryDetails("190")
            //Then
            responseFlow.collectLatest {
                Assert.assertEquals(
                    AppConstants.API_RESPONSE_ERROR,
                    viewModel.state.value.errorCode
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `countryDetailsViewModel should update state with error code when fetching country details with exception`() =
        runTest {
            //Given
            val isNetworkAvailable = true
            val responseFlow: Flow<Response<CountryDetailsModel>> = flow {
                emit(Response.Exception(Throwable(message = "Test")))
            }
            //When
            every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
            coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow
            viewModel.fetchCountryDetails("190")

            //Then
            responseFlow.collectLatest {
                Assert.assertEquals(
                    AppConstants.API_RESPONSE_ERROR,
                    viewModel.state.value.errorCode
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get country details no internet case test`() = runTest {
        //Given
        val isNetworkAvailable = false
        val responseFlow: Flow<Response<CountryDetailsModel>> = flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow
        viewModel.fetchCountryDetails("190")

        //Then
        responseFlow.collectLatest {
            Assert.assertEquals(AppConstants.INTERNET_ERROR, viewModel.state.value.errorCode)
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}