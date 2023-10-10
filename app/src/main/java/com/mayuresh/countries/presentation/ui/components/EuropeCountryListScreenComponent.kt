package com.mayuresh.countries.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mayuresh.countries.R
import com.mayuresh.countries.data.util.AppConstants
import com.mayuresh.countries.domain.model.CountryListModel
import com.mayuresh.countries.presentation.ui.theme.EuropeCountryTypography
import com.mayuresh.countries.presentation.ui.theme.EuropeanCountriesShapes
import com.mayuresh.countries.presentation.viewmodel.CountryListViewModel

/**
 * This composable is responsible for list view representation of articles
 * @param viewModel
 * @param onCountrySelection
 */
@Composable
fun EuropeCountryListScreenComponent(
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = hiltViewModel(),
    onCountrySelection: (CountryListModel?) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> ProgressBarComponent(modifier = Modifier.fillMaxWidth())
            state.errorCode != 0 ->
                Text(
                    text = if (state.errorCode == AppConstants.API_RESPONSE_ERROR) {
                        stringResource(id = R.string.internet_error_message)
                    } else {
                        stringResource(
                            id = R.string.api_error_message,
                        )
                    },
                    style = EuropeCountryTypography.labelSmall,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )

            else -> LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(state.countries) { country ->
                    CountryListItem(country) {
                        onCountrySelection.invoke(it)
                    }
                }
            }
        }
    }
}

/**
 * This composable is used for article list item view
 * @param country,
 * @param selectedCountry
 */
@Composable
fun CountryListItem(
    country: CountryListModel,
    selectedCountry: (CountryListModel) -> Unit,
) {
    Card(
        shape = EuropeanCountriesShapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selectedCountry.invoke(country)
            }
            .padding(horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val placeholderImage = R.drawable.thumbnail
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flagImageUrl)
                    .placeholder(placeholderImage)
                    .build(),
                error = painterResource(placeholderImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(50.dp)
                    .width(50.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = country.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = EuropeCountryTypography.labelMedium,
                    fontSize = 13.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = country.region.plus(" ( " + country.subregion + ")"),
                    maxLines = 1,
                    style = EuropeCountryTypography.labelMedium,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
