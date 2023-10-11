package com.mayuresh.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mayuresh.common.components.ProgressBarComponent
import com.mayuresh.common.theme.EuropeCountryTypography
import com.mayuresh.common.theme.EuropeanCountriesShapes
import com.mayuresh.common.R
import com.mayuresh.data.model.CountryDetailsModel
import com.mayuresh.data.util.AppConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This is detail screen of country
 * @param modifier
 * @param viewModel
 * @param countryCode
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CountryDetailsScreenComponent(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel = hiltViewModel(),
    countryCode: String,
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.processIntent(CountryDetailsIntent.FetchCountryDetails(countryCode))
    })
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

            else -> CountryDetailsComponent(
                modifier = Modifier.fillMaxSize(),
                country = state.country,
            )
        }
    }
}

@Composable
fun CountryDetailsComponent(
    modifier: Modifier = Modifier,
    country: CountryDetailsModel,
) {
    val placeholderImage = R.drawable.thumbnail
    Box(modifier = modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(country.flagImageUrl)
                .placeholder(placeholderImage)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = placeholderImage),
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(300.dp)
                .border(BorderStroke(3.dp, MaterialTheme.colorScheme.primary))
                .background(MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = EuropeanCountriesShapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append(stringResource(id = R.string.region).plus(" "))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                            ),
                        ) {
                            append(country.region)
                        }
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = EuropeCountryTypography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append(stringResource(id = R.string.subregion).plus(" "))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                            ),
                        ) {
                            append(country.subregion)
                        }
                    },
                    maxLines = 1,
                    style = EuropeCountryTypography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append(stringResource(id = R.string.population).plus(" "))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                            ),
                        ) {
                            append(country.population)
                        }
                    },
                    maxLines = 1,
                    style = EuropeCountryTypography.titleMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append(stringResource(id = R.string.currency).plus(" "))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                            ),
                        ) {
                            append(country.currencies)
                        }
                    },
                    maxLines = 2,
                    style = EuropeCountryTypography.titleMedium,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            append(stringResource(id = R.string.language).plus(" "))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                            ),
                        ) {
                            append(country.languages)
                        }
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = EuropeCountryTypography.titleMedium,
                )
            }
        }
    }
    }
}
