package com.mayuresh.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("side")
    @Expose
    val side: String?,
    @SerializedName("signs")
    @Expose
    val signs: List<String>?,
)
