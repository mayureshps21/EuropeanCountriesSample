package com.mayuresh.data.dto.countryinfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DemonymEnglishDto(
    @SerializedName("f")
    @Expose
    val female: String,
    @SerializedName("m")
    @Expose
    val male: String,
)
