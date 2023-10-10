package com.mayuresh.countries.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayuresh.countries.data.util.AppConstants
import com.mayuresh.countries.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.countries.domain.util.Response
import com.mayuresh.countries.presentation.intent.CountryDetailsIntent
import com.mayuresh.countries.presentation.state.CountryDetailsUiState
import com.mayuresh.countries.presentation.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is responsible for pass countries list to composable functions
 * @param getCountriesDetailsUseCase
 * @param networkHelper
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val getCountriesDetailsUseCase: GetCountriesDetailsUseCase,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _state = MutableStateFlow(CountryDetailsUiState(isLoading = true))
    val state: StateFlow<CountryDetailsUiState> get() = _state

    fun processIntent(intent: CountryDetailsIntent) {
        when (intent) {
            is CountryDetailsIntent.FetchCountryDetails -> fetchCountryDetails(intent.data)
        }
    }

    private fun fetchCountryDetails(countryCode: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                getCountriesDetailsUseCase.invoke(countryCode)
                    .collect() { response ->
                        when (response) {
                            is Response.Success -> {
                                _state.value = CountryDetailsUiState(
                                    country = response.data,
                                    isLoading = false,
                                )
                            }

                            is Response.Error -> {
                                _state.value = CountryDetailsUiState(
                                    errorCode = AppConstants.API_RESPONSE_ERROR,
                                    isLoading = false,
                                )
                            }

                            is Response.Exception -> {
                                _state.value = CountryDetailsUiState(
                                    errorCode = AppConstants.API_RESPONSE_ERROR,
                                    isLoading = false,
                                )
                            }
                        }
                    }
            } else {
                _state.value = CountryDetailsUiState(
                    errorCode = AppConstants.INTERNET_ERROR,
                    isLoading = false,
                )
            }
        }
    }
}
