package com.mayuresh.countries.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CapitalInfoDto(
    @SerializedName("latlng")
    @Expose
    val latlng: List<Double>?,
)
