package com.mayuresh.countries.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MapsDto(
    @SerializedName("googleMaps")
    @Expose
    val googleMaps: String,
    @SerializedName("openStreetMaps")
    @Expose
    val openStreetMaps: String,
)
