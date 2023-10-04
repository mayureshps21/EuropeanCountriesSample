package com.mayuresh.countries.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayuresh.countries.data.util.Response
import com.mayuresh.countries.domain.usecase.GetEuropeanCountriesUseCase
import com.mayuresh.countries.presentation.intent.CountriesIntent
import com.mayuresh.countries.presentation.state.CountriesListState
import com.mayuresh.countries.presentation.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This view model is responsible for pass article list to composable functions
 * @param getEuropeanCountriesUseCase
 * @param networkHelper
 */
@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getEuropeanCountriesUseCase: GetEuropeanCountriesUseCase,
    val networkHelper: NetworkHelper
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesListState(isLoading = true))
    val state: StateFlow<CountriesListState> get() = _state

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
                                _state.value = CountriesListState(
                                    countries = response.data,
                                    isLoading = false
                                )
                            }

                            is Response.Error -> {
                                _state.value = CountriesListState(
                                    errorCode = 400,
                                    isLoading = false
                                )
                            }

                            else -> {
                                _state.value = CountriesListState(
                                    errorCode = 400,
                                    isLoading = false
                                )
                            }
                        }
                    }
            } else {
                _state.value = CountriesListState(
                    errorCode = 100,
                    isLoading = false
                )
            }
        }
    }
}