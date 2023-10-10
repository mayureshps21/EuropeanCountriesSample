package com.mayuresh.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NameDto(
    @SerializedName("common")
    @Expose
    val common: String,
    @SerializedName("nativeName")
    @Expose
    val nativeName: Pair<String?, String?>?,
    @SerializedName("official")
    @Expose
    val official: String,
)
