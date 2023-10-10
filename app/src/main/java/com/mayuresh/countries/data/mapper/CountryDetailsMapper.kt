package com.mayuresh.countries.data.mapper

import com.mayuresh.countries.data.dto.CountryDto
import com.mayuresh.countries.data.dto.countryinfo.CurrencyDto
import com.mayuresh.countries.domain.model.CountryDetailsModel
import com.mayuresh.countries.domain.util.toReadable
import javax.inject.Inject

/**
 * This class is used for map the server response to desired response.
 */
class CountryDetailsMapper @Inject constructor() : Mapper<CountryDto, CountryDetailsModel> {

    override fun mapFrom(from: CountryDto): CountryDetailsModel {
        return CountryDetailsModel(
            name = from.nameDto.common,
            flagImageUrl = from.flagsDto?.png ?: "",
            population = from.population.toReadable(),
            region = from.region ?: "",
            subregion = from.subregion ?: "",
            currencies = getCommaSeparatedCurrency(from.currencies),
            languages = getCommaSeparatedLanguage(from.languages),
            googleMapUrl = from.mapsDto?.googleMaps ?: "",
        )
    }

    private fun getCommaSeparatedCurrency(map: Map<String, CurrencyDto>?): String {
        val str = StringBuilder("")
        if (!map.isNullOrEmpty()) {
            for ((key, currency) in map) {
                if (str.isNotEmpty()) str.append(", ") else str.append("${currency.name} (${currency.symbol})")
            }
        }
        return str.toString()
    }

    private fun getCommaSeparatedLanguage(map: Map<String, String>?): String {
        val str = StringBuilder("")
        if (!map.isNullOrEmpty()) {
            for ((key, name) in map) {
                if (str.isNotEmpty()) str.append(", ") else str.append(name)
            }
        }
        return str.toString()
    }
}
