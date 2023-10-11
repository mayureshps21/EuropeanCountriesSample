package com.mayuresh.countries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayuresh.countries.R
import com.mayuresh.detail.view.CountryDetailsScreenComponent
import com.mayuresh.home.view.EuropeCountryListScreenComponent
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
            actionBarTitle.invoke(stringResource(id = R.string.title))
            isShowBackButton.invoke(false)
            EuropeCountryListScreenComponent(
                onCountrySelection = {
                    navController.navigate(
                        route = CountryDestinations.COUNTRY_DETAIL_ROUTE.plus("/")
                            .plus(it?.countryCode ?: ""),
                    )
                }
            )
        }
        composable(route = CountryDestinations.COUNTRY_DETAIL_ROUTE.plus("/{code}")) { backStackEntry ->
            CountryDetailsScreenComponent(
                countryCode = backStackEntry.arguments?.getString("code") ?: "",
                actionBarTitle = actionBarTitle,
                isShowBackButton = isShowBackButton
            )
        }
    }
}
