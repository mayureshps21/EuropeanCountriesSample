package com.mayuresh.countries.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mayuresh.countries.data.model.countryinfo.*

data class CountryModel(
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
    val capitalInfo: CapitalInfo?,
    @SerializedName("car")
    @Expose
    val car: Car?,
    @SerializedName("map")
    @Expose
    val maps: Maps?,
    @SerializedName("coatOfArms")
    @Expose
    val coatOfArms: CoatOfArms?,
    @SerializedName("continents")
    @Expose
    val continents: List<String>?,
    @SerializedName("demonyms")
    @Expose
    val demonyms: Demonyms?,
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
    val flags: Flags?,
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
    var name: Name,
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
    val unMember: Boolean
) {

    @SerializedName("currencies")
    @Expose
    var currencies: Map<String, Currency>? = emptyMap()

    @SerializedName("languages")
    @Expose
    var languages: Map<String, String>? = emptyMap()

    @SerializedName("translations")
    @Expose
    var translations: Map<String, Translation>? = emptyMap()

}