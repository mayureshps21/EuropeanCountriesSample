package com.mayuresh.home.intent

import com.mayuresh.common.base.UiEvent
import com.mayuresh.data.model.CountryListModel
import javax.annotation.concurrent.Immutable

@Immutable
sealed class CountryListScreenUiEvent : UiEvent {
    data class ShowData(val data: List<CountryListModel>) : CountryListScreenUiEvent()
    object OnAPIError : CountryListScreenUiEvent()
    object OnInternetError : CountryListScreenUiEvent()
    data class IsLoading(val isLoading: Boolean) : CountryListScreenUiEvent()

}