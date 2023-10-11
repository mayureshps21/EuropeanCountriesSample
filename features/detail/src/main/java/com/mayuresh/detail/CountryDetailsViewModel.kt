package com.mayuresh.detail

import androidx.lifecycle.viewModelScope
import com.mayuresh.common.base.BaseViewModel
import com.mayuresh.common.base.Reducer
import com.mayuresh.common.util.NetworkHelper
import com.mayuresh.data.util.AppConstants
import com.mayuresh.detail.intent.CountryDetailsScreenUiEvent
import com.mayuresh.detail.model.CountryDetailsUiState
import com.mayuresh.domain.usecase.GetCountriesDetailsUseCase
import com.mayuresh.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
) : BaseViewModel<CountryDetailsUiState, CountryDetailsScreenUiEvent>() {
    private val reducer = MainReducer(CountryDetailsUiState.initial())

    override val state: StateFlow<CountryDetailsUiState>
        get() = reducer.state

    private fun sendEvent(event: CountryDetailsScreenUiEvent) {
        reducer.sendEvent(event)
    }

    fun fetchCountryDetails(countryCode: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                getCountriesDetailsUseCase.invoke(countryCode)
                    .collect() { response ->
                        when (response) {
                            is Response.Success -> {
                                sendEvent(CountryDetailsScreenUiEvent.ShowData(data = response.data))
                                sendEvent(CountryDetailsScreenUiEvent.IsLoading(isLoading = false))
                            }

                            is Response.Error -> {
                                sendEvent(CountryDetailsScreenUiEvent.OnAPIError)
                            }

                            is Response.Exception -> {
                                sendEvent(CountryDetailsScreenUiEvent.OnAPIError)
                            }
                        }
                    }
            } else {
                sendEvent(CountryDetailsScreenUiEvent.OnInternetError)
            }
        }
    }

    private class MainReducer(initial: CountryDetailsUiState) :
        Reducer<CountryDetailsUiState, CountryDetailsScreenUiEvent>(initial) {
        override fun reduce(oldState: CountryDetailsUiState, event: CountryDetailsScreenUiEvent) {
            when (event) {
                is CountryDetailsScreenUiEvent.ShowData -> {
                    setState(oldState.copy(country = event.data))
                }

                is CountryDetailsScreenUiEvent.IsLoading -> {
                    setState(oldState.copy(isLoading = event.isLoading))
                }

                is CountryDetailsScreenUiEvent.OnAPIError -> {
                    setState(oldState.copy(errorCode = AppConstants.API_RESPONSE_ERROR))
                }

                is CountryDetailsScreenUiEvent.OnInternetError -> {
                    setState(oldState.copy(errorCode = AppConstants.INTERNET_ERROR))
                }
            }
        }
    }
}
