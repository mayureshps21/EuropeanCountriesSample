package com.mayuresh.countries.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.countries.MainCoroutineRule
import com.mayuresh.countries.data.util.AppConstants
import com.mayuresh.countries.domain.model.CountryListModel
import com.mayuresh.countries.domain.usecase.GetEuropeanCountriesUseCase
import com.mayuresh.countries.domain.util.Response
import com.mayuresh.countries.presentation.intent.CountriesIntent
import com.mayuresh.countries.presentation.util.NetworkHelper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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

class CountryListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getEuropeanCountryUseCase: GetEuropeanCountriesUseCase

    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewModel: CountryListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        networkHelper = mockk(relaxed = true)
        viewModel = CountryListViewModel(
            getEuropeanCountriesUseCase = getEuropeanCountryUseCase,
            networkHelper = networkHelper,
        )
    }
    @Test
    fun `countriesListViewModel should update state with expected data when fetching countries list with success and network available`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListModel>()
        countriesList.add(CountryListModel("Test"))
        val responseFlow: Flow<Response<List<CountryListModel>>> = flow {
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
    fun `countriesListViewModel should update state with expected data when fetching empty countries list with success and network available`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListModel>()
        val responseFlow: Flow<Response<List<CountryListModel>>> = flow {
            emit(Response.Success(countriesList))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow

        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        responseFlow.collectLatest {
            Assert.assertEquals(0, viewModel.state.value.countries.size)
        }
    }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with network not available`() = runTest {
        //Given
        val isNetworkAvailable = false
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        Assert.assertTrue(viewModel.state.value.errorCode == AppConstants.INTERNET_ERROR)
    }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with error and network available`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListModel>()
        countriesList.add(CountryListModel("Test"))
        val responseFlow: Flow<Response<List<CountryListModel>>> = flow {
            emit(Response.Error(code = AppConstants.API_RESPONSE_ERROR, "Test"))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow

        //Then
        viewModel.processIntent(CountriesIntent.LoadCountries)
        responseFlow.collectLatest {
            Assert.assertEquals(AppConstants.API_RESPONSE_ERROR, viewModel.state.value.errorCode)
        }
    }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with exception and network available`() = runTest {
        //Given
        val isNetworkAvailable = true
        val countriesList = mutableListOf<CountryListModel>()
        countriesList.add(CountryListModel("Test"))
        val responseFlow: Flow<Response<List<CountryListModel>>> = flow {
            emit(Response.Exception(Throwable(message = "Test")))
        }
        //When
        every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
        coEvery { getEuropeanCountryUseCase.invoke() } returns responseFlow
        viewModel.processIntent(CountriesIntent.LoadCountries)
        //Then
        responseFlow.collectLatest {
            Assert.assertEquals(AppConstants.API_RESPONSE_ERROR, viewModel.state.value.errorCode)
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

}