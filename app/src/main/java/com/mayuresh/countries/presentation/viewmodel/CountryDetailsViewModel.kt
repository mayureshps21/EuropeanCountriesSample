package com.mayuresh.countries.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayuresh.countries.data.util.Response
import com.mayuresh.countries.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.countries.presentation.intent.CountryDetailsIntent
import com.mayuresh.countries.presentation.state.CountryDetailsState
import com.mayuresh.countries.presentation.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is responsible for pass article list to composable functions
 * @param getCountriesDetailsUseCase
 * @param networkHelper
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val getCountriesDetailsUseCase: GetCountriesDetailsUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _state = MutableStateFlow(CountryDetailsState(isLoading = true))
    val state: StateFlow<CountryDetailsState> get() = _state

    fun processIntent(intent: CountryDetailsIntent) {
        when (intent) {
            is CountryDetailsIntent.FetchCountryDetails -> fetchCountries(intent.data)
        }
    }

    private fun fetchCountries(countryCode: String) {
        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    getCountriesDetailsUseCase.invoke(countryCode)
                        .collect() { response ->
                            when (response) {
                                is Response.Success -> {
                                    _state.value = CountryDetailsState(
                                        country = response.data,
                                        isLoading = false
                                    )
                                }

                                is Response.Error -> {
                                    _state.value = CountryDetailsState(
                                        errorCode = 400,
                                        isLoading = false
                                    )
                                }

                                else -> {
                                    _state.value = CountryDetailsState(
                                        errorCode = 500,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                } else {
                    _state.value = CountryDetailsState(
                        errorCode = 100,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = CountryDetailsState(
                    errorCode = 100,
                    isLoading = false
                )
            }
        }
    }
}