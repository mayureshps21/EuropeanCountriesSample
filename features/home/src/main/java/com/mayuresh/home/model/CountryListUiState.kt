package com.mayuresh.home.model

import com.mayuresh.common.base.UiState
import com.mayuresh.data.model.CountryListModel
import javax.annotation.concurrent.Immutable

@Immutable
data class CountryListUiState(
    val isLoading: Boolean,
    val countries: List<CountryListModel> = emptyList(),
    val errorCode: Int = 0,
) : UiState {

    companion object {
        fun initial() = CountryListUiState(
            isLoading = true,
        )
    }
}