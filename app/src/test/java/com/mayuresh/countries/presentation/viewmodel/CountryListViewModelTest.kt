package com.mayuresh.countries.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.countries.MainCoroutineRule
import com.mayuresh.countries.data.util.Response
import com.mayuresh.countries.domain.model.CountryListUiState
import com.mayuresh.countries.domain.usecase.GetEuropeanCountriesUseCase
import com.mayuresh.countries.presentation.intent.CountriesIntent
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

class CountryListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var getEuropeanCountryUseCase: GetEuropeanCountriesUseCase

    @RelaxedMockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewModel: CountryListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        viewModel = CountryListViewModel(
            getEuropeanCountriesUseCase = getEuropeanCountryUseCase,
            networkHelper = networkHelper,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get countries list success test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListUiState>()
        countriesList.add(CountryListUiState("Test"))
        val responseFlow: Flow<Response<List<CountryListUiState>>> = flow {
            emit(Response.Success(countriesList))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow

        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        responseFlow.collectLatest {
            Assert.assertEquals("Test", viewModel.state.value.countries.get(0).name)
        }
    }

    @Test
    fun `get countries list error test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListUiState>()
        countriesList.add(CountryListUiState("Test"))
        val responseFlow: Flow<Response<List<CountryListUiState>>> = flow {
            emit(Response.Error(code = 400, "Test"))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow

        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        responseFlow.collectLatest {
            Assert.assertEquals(400, viewModel.state.value.errorCode)
        }
    }

    @Test
    fun `get countries list exception case test`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListUiState>()
        countriesList.add(CountryListUiState("Test"))
        val responseFlow: Flow<Response<List<CountryListUiState>>> = flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow

        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        responseFlow.collectLatest {
            Assert.assertEquals(400, viewModel.state.value.errorCode)
        }
    }

}