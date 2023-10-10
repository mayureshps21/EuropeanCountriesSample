package com.mayuresh.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mayuresh.data.dto.countryinfo.CapitalInfoDto
import com.mayuresh.data.dto.countryinfo.CarDto
import com.mayuresh.data.dto.countryinfo.CoatOfArmsDto
import com.mayuresh.data.dto.countryinfo.CurrencyDto
import com.mayuresh.data.dto.countryinfo.DemonymsDto
import com.mayuresh.data.dto.countryinfo.FlagsDto
import com.mayuresh.data.dto.countryinfo.MapsDto
import com.mayuresh.data.dto.countryinfo.NameDto
import com.mayuresh.data.dto.countryinfo.TranslationDto
import com.mayuresh.data.dto.countryinfo.*

data class CountryDto(
    @Transient
    val roomId: Long,
    @SerializedName("altSpellings")
    @Expose
    val altSpellings: List<String>?,
    @SerializedName("area")
    @Expose
    val area: Float,
    @SerializedName("borders")
    @Expose
    val borders: List<String>?,
    @SerializedName("capital")
    @Expose
    val capital: List<String>?,
    @SerializedName("capitalInfo")
    @Expose
    val capitalInfoDto: CapitalInfoDto?,
    @SerializedName("car")
    @Expose
    val carDto: CarDto?,
    @SerializedName("map")
    @Expose
    val mapsDto: MapsDto?,
    @SerializedName("coatOfArms")
    @Expose
    val coatOfArmsDto: CoatOfArmsDto?,
    @SerializedName("continents")
    @Expose
    val continents: List<String>?,
    @SerializedName("demonyms")
    @Expose
    val demonymsDto: DemonymsDto?,
    @SerializedName("ccn3")
    @Expose
    val ccn3: String?,
    @SerializedName("fifa")
    @Expose
    val fifa: String?,
    @SerializedName("flag")
    @Expose
    val flag: String?,
    @SerializedName("flags")
    @Expose
    val flagsDto: FlagsDto?,
    @SerializedName("independent")
    @Expose
    val independent: Boolean,
    @SerializedName("landlocked")
    @Expose
    val landlocked: Boolean,
    @SerializedName("latlng")
    @Expose
    val latlng: List<Double>?,
    @SerializedName("name")
    @Expose
    var nameDto: NameDto,
    @SerializedName("population")
    @Expose
    val population: Int,
    @SerializedName("region")
    @Expose
    val region: String?,
    @SerializedName("startOfWeek")
    @Expose
    val startOfWeek: String?,
    @SerializedName("status")
    @Expose
    val status: String?,
    @SerializedName("subregion")
    @Expose
    val subregion: String?,
    @SerializedName("timezones")
    @Expose
    val timezones: List<String>?,
    @SerializedName("tld")
    @Expose
    val tld: List<String>?,
    @SerializedName("unMember")
    @Expose
    val unMember: Boolean,
) {

    @SerializedName("currencies")
    @Expose
    var currencies: Map<String, CurrencyDto>? = emptyMap()

    @SerializedName("languages")
    @Expose
    var languages: Map<String, String>? = emptyMap()

    @SerializedName("translations")
    @Expose
    var translations: Map<String, TranslationDto>? = emptyMap()
}
