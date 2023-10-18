package com.mayuresh.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mayuresh.common.util.NetworkHelper
import com.mayuresh.data.model.CountryListModel
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.usecase.GetEuropeanCountriesUseCase
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

class CountryListViewModelTest {

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
            getEuropeanCountries = getEuropeanCountryUseCase,
            networkHelper = networkHelper,
        )
    }

    @Test
    fun `countriesListViewModel should update state with expected data when fetching countries list with success and network available`() =
        runTest {
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
            viewModel.loadCountries()
            responseFlow.collectLatest {
                Assert.assertEquals("Test", viewModel.state.value.countries.get(0).name)
            }
        }

    @Test
    fun `countriesListViewModel should update state with expected data when fetching empty countries list with success and network available`() =
        runTest {
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
            viewModel.loadCountries()
            responseFlow.collectLatest {
                Assert.assertEquals(0, viewModel.state.value.countries.size)
            }
        }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with network not available`() =
        runTest {
            //Given
            val isNetworkAvailable = false
            //When
            every { networkHelper.isNetworkConnected() } returns isNetworkAvailable
            //Then
            viewModel.loadCountries()
            Assert.assertTrue(viewModel.state.value.errorCode == AppConstants.INTERNET_ERROR)
        }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with error and network available`() =
        runTest {
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
            viewModel.loadCountries()
            responseFlow.collectLatest {
                Assert.assertEquals(
                    AppConstants.API_RESPONSE_ERROR,
                    viewModel.state.value.errorCode
                )
            }
        }

    @Test
    fun `countriesListViewModel should update state with error code when fetching countries list with exception and network available`() =
        runTest {
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
            viewModel.loadCountries()
            //Then
            responseFlow.collectLatest {
                Assert.assertEquals(
                    AppConstants.API_RESPONSE_ERROR,
                    viewModel.state.value.errorCode
                )
            }
        }

    @After
    fun tearDown() {
        clearAllMocks()
    }

}