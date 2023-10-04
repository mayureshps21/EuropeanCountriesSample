package com.mayuresh.countries.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.countries.MainCoroutineRule
import com.mayuresh.countries.data.util.Response
import com.mayuresh.countries.domain.model.CountryDetailsUiState
import com.mayuresh.countries.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.countries.presentation.intent.CountryDetailsIntent
import com.mayuresh.countries.presentation.util.NetworkHelper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
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

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var getCountriesDetailsUseCase: GetCountriesDetailsUseCase

    @RelaxedMockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewModel: CountryDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        viewModel = CountryDetailsViewModel(
            getCountriesDetailsUseCase = getCountriesDetailsUseCase,
            networkHelper = networkHelper,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get country details success test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val country = CountryDetailsUiState("Florida")
        val responseFlow: Flow<Response<CountryDetailsUiState>> = flow {
            emit(Response.Success(country))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow

        //Then
        viewModel.processIntent(CountryDetailsIntent.FetchCountryDetails("190"))
        responseFlow.collectLatest {
            Assert.assertEquals("Florida", viewModel.state.value.country.name)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get countries list error test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val responseFlow: Flow<Response<CountryDetailsUiState>> = flow {
            emit(Response.Error(code = 400, "Test"))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow

        //Then
        viewModel.processIntent(CountryDetailsIntent.FetchCountryDetails("190"))
        responseFlow.collectLatest {
            Assert.assertEquals(400, viewModel.state.value.errorCode)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get countries list exception case test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val responseFlow: Flow<Response<CountryDetailsUiState>> = flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getCountriesDetailsUseCase.invoke("190") } returns responseFlow

        //Then
        viewModel.processIntent(CountryDetailsIntent.FetchCountryDetails("190"))
        responseFlow.collectLatest {
            Assert.assertEquals(500, viewModel.state.value.errorCode)
        }
    }
}