package com.mayuresh.detail.intent

import com.mayuresh.common.base.UiEvent
import com.mayuresh.data.model.CountryDetailsModel
import javax.annotation.concurrent.Immutable

@Immutable
sealed class CountryDetailsScreenUiEvent : UiEvent {
    data class ShowData(val data: CountryDetailsModel) : CountryDetailsScreenUiEvent()
    object OnAPIError : CountryDetailsScreenUiEvent()
    object OnInternetError : CountryDetailsScreenUiEvent()
    data class IsLoading(val isLoading: Boolean) : CountryDetailsScreenUiEvent()

}