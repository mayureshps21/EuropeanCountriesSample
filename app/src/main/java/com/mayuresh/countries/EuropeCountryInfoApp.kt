package com.mayuresh.countries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.mayuresh.common.components.AppToolbarComponent
import com.mayuresh.common.theme.EuropeanCountriesTheme
import com.mayuresh.countries.navigation.EuropeanCountriesNavGraph

@Composable
fun EuropeanCountriesApp() {
    EuropeanCountriesTheme {
        val navController = rememberNavController()
        var isShowBackButton by remember { mutableStateOf(false) }
        val defaultTitle = stringResource(id = R.string.title)
        var actionBarTitle by remember { mutableStateOf(defaultTitle) }

        Scaffold(
            topBar = {
                AppToolbarComponent(
                    title = actionBarTitle,
                    onUpClick = {
                        navController.navigateUp()
                    },
                    isShowBackButton = isShowBackButton,
                )
            },
            content = { paddingValues ->
                EuropeanCountriesNavGraph(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    actionBarTitle = {
                        actionBarTitle = it ?: ""
                    },
                    isShowBackButton = {
                        isShowBackButton = it
                    }
                )
            },
        )
    }
}
