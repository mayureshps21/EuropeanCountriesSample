package com.mayuresh.countries.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoatOfArmsDto(
    @SerializedName("png")
    @Expose
    val png: String?,
)
