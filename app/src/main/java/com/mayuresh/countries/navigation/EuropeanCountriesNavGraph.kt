package com.mayuresh.countries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayuresh.detail.CountryDetailsScreenComponent
import com.mayuresh.home.EuropeCountryListScreenComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun EuropeanCountriesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CountryDestinations.COUNTRY_LIST_ROUTE,
    isShowBackButton: (Boolean) -> Unit,
    actionBarTitle: (String?) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(
            route = CountryDestinations.COUNTRY_LIST_ROUTE,
        ) {
            isShowBackButton.invoke(false)
            EuropeCountryListScreenComponent(onCountrySelection = {
                navController.navigate(
                    route = CountryDestinations.COUNTRY_DETAIL_ROUTE.plus("/")
                        .plus(it?.countryCode ?: ""),
                )
                actionBarTitle.invoke(it?.name)
            })
        }
        composable(route = CountryDestinations.COUNTRY_DETAIL_ROUTE.plus("/{code}")) { backStackEntry ->
            isShowBackButton.invoke(true)
            CountryDetailsScreenComponent(
                countryCode = backStackEntry.arguments?.getString("code") ?: "",
            )
        }
    }
}
