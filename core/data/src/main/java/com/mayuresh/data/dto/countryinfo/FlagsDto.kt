package com.mayuresh.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FlagsDto(
    @SerializedName("png")
    @Expose
    val png: String,
    @SerializedName("alt")
    @Expose
    val alt: String,
)
