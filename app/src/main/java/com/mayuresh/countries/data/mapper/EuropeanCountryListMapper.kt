package com.mayuresh.countries.data.mapper

import com.mayuresh.countries.data.dto.CountryDto
import com.mayuresh.countries.domain.model.CountryListModel
import javax.inject.Inject

/**
 * This class is used for map the server response to desired response.
 */
class EuropeanCountryListMapper @Inject constructor() :
    Mapper<List<CountryDto>, List<CountryListModel>> {

    override fun mapFrom(fromList: List<CountryDto>): List<CountryListModel> {
        val countryList = mutableListOf<CountryListModel>()
        for (from in fromList) {
            countryList.add(
                CountryListModel(
                    name = from.nameDto.common,
                    flagImageUrl = from.flagsDto?.png ?: "",
                    countryCode = from.ccn3 ?: "",
                    region = from.region ?: "",
                    subregion = from.subregion ?: "",
                ),
            )
        }
        return countryList
    }
}
