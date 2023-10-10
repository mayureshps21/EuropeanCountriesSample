package com.mayuresh.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("symbol")
    @Expose
    val symbol: String,
)
