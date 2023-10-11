package com.mayuresh.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayuresh.common.util.NetworkHelper
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.usecase.GetEuropeanCountriesUseCase
import com.mayuresh.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is responsible for pass countries list to composable functions
 * @param getEuropeanCountriesUseCase
 * @param networkHelper
 */
@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getEuropeanCountriesUseCase: GetEuropeanCountriesUseCase,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesListUiState(isLoading = true))
    val state: StateFlow<CountriesListUiState> get() = _state

    init {
        processIntent(CountriesIntent.LoadCountries)
    }

    fun processIntent(intent: CountriesIntent) {
        when (intent) {
            is CountriesIntent.LoadCountries -> loadCountries()
        }
    }

    private fun loadCountries() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                getEuropeanCountriesUseCase.invoke()
                    .collect() { response ->
                        when (response) {
                            is Response.Success -> {
                                _state.value = CountriesListUiState(
                                    countries = response.data,
                                    isLoading = false,
                                )
                            }

                            is Response.Error -> {
                                _state.value = CountriesListUiState(
                                    errorCode = AppConstants.API_RESPONSE_ERROR,
                                    isLoading = false,
                                )
                            }

                            is Response.Exception -> {
                                _state.value = CountriesListUiState(
                                    errorCode = AppConstants.API_RESPONSE_ERROR,
                                    isLoading = false,
                                )
                            }
                        }
                    }
            } else {
                _state.value = CountriesListUiState(
                    errorCode = AppConstants.INTERNET_ERROR,
                    isLoading = false,
                )
            }
        }
    }
}
