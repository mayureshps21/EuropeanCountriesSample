package com.mayuresh.countries.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TranslationDto(
    @SerializedName("common")
    @Expose
    val common: String,
    @SerializedName("official")
    @Expose
    val official: String,
)
