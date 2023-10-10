package com.mayuresh.domain.mapper

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.model.CountryDetailsModel
import com.mayuresh.domain.util.toReadable
import javax.inject.Inject

/**
 * This class is used for map the server response to desired response.
 */
class CountryDetailsMapper @Inject constructor() : Mapper<CountryDto, CountryDetailsModel> {
    override fun mapFrom(from: CountryDto): CountryDetailsModel {
       val currencies = from.currencies?.values?.joinToString(separator = ", ") { "${it.name} (${it.symbol})" } ?: ""
       val languages = from.languages?.values?.joinToString(separator = ", ") ?: ""

       return CountryDetailsModel(
           name = from.nameDto.common,
           flagImageUrl = from.flagsDto?.png.orEmpty(),
           population = from.population.toReadable(),
           region = from.region.orEmpty(),
           subregion = from.subregion.orEmpty(),
           currencies = currencies,
           languages = languages,
           googleMapUrl = from.mapsDto?.googleMaps.orEmpty(),
       )
   }
}
