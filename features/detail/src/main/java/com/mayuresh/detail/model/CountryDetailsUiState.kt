package com.mayuresh.detail.model

import com.mayuresh.common.base.UiState
import com.mayuresh.data.model.CountryDetailsModel
import javax.annotation.concurrent.Immutable

@Immutable
data class CountryDetailsUiState(
    val isLoading: Boolean,
    val country: CountryDetailsModel = CountryDetailsModel(),
    val errorCode: Int = 0,
) : UiState {

    companion object {
        fun initial() = CountryDetailsUiState(
            isLoading = true,
        )
    }
}