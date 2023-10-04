package com.mayuresh.countries.presentation.intent

/**
 * List Interaction Intents
 */
sealed class CountriesIntent{
    object LoadCountries : CountriesIntent()
}
