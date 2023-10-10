package com.mayuresh.domain.mapper

import com.mayuresh.data.dto.CountryDto
import com.mayuresh.data.model.CountryListModel
import javax.inject.Inject

/**
 * This class is used for map the server response to desired response.
 */
class EuropeanCountryListMapper @Inject constructor() :
    Mapper<List<CountryDto>, List<CountryListModel>> {

    override fun mapFrom(from: List<CountryDto>): List<CountryListModel> {
        return from.map { fromObj ->
            CountryListModel(
                name = fromObj.nameDto.common,
                flagImageUrl = fromObj.flagsDto?.png.orEmpty(),
                countryCode = fromObj.ccn3.orEmpty(),
                region = fromObj.region.orEmpty(),
                subregion = fromObj.subregion.orEmpty(),
            )
        }
    }
}
