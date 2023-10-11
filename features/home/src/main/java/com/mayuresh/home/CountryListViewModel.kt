package com.mayuresh.home

import androidx.lifecycle.viewModelScope
import com.mayuresh.common.base.BaseViewModel
import com.mayuresh.common.base.Reducer
import com.mayuresh.common.util.NetworkHelper
import com.mayuresh.data.util.AppConstants
import com.mayuresh.domain.usecase.GetEuropeanCountriesUseCase
import com.mayuresh.domain.util.Response
import com.mayuresh.home.intent.CountryListScreenUiEvent
import com.mayuresh.home.model.CountryListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : BaseViewModel<CountryListUiState, CountryListScreenUiEvent>() {

    private val reducer = MainReducer(CountryListUiState.initial())

    override val state: StateFlow<CountryListUiState>
        get() = reducer.state

    private fun sendEvent(event: CountryListScreenUiEvent) {
        reducer.sendEvent(event)
    }

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                getEuropeanCountriesUseCase.invoke()
                    .collect() { response ->
                        when (response) {
                            is Response.Success -> {
                                sendEvent(CountryListScreenUiEvent.ShowData(data = response.data))
                                sendEvent(CountryListScreenUiEvent.IsLoading(isLoading = false))
                            }

                            is Response.Error -> {
                                sendEvent(CountryListScreenUiEvent.OnAPIError)
                            }

                            is Response.Exception -> {
                                sendEvent(CountryListScreenUiEvent.OnAPIError)
                            }
                        }
                    }
            } else {
                sendEvent(CountryListScreenUiEvent.OnInternetError)
            }
        }
    }

    private class MainReducer(initial: CountryListUiState) :
        Reducer<CountryListUiState, CountryListScreenUiEvent>(initial) {
        override fun reduce(oldState: CountryListUiState, event: CountryListScreenUiEvent) {
            when (event) {
                is CountryListScreenUiEvent.ShowData -> {
                    setState(oldState.copy(countries = event.data))
                }

                is CountryListScreenUiEvent.IsLoading -> {
                    setState(oldState.copy(isLoading = event.isLoading))
                }

                is CountryListScreenUiEvent.OnAPIError -> {
                    setState(oldState.copy(errorCode = AppConstants.API_RESPONSE_ERROR))
                }

                is CountryListScreenUiEvent.OnInternetError -> {
                    setState(oldState.copy(errorCode = AppConstants.INTERNET_ERROR))
                }
            }
        }
    }
}