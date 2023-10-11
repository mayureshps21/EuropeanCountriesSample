package com.mayuresh.common.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mayuresh.common.theme.EuropeCountryTypography

/**
 * This is common toolbar
 * @param title // title name
 * @param onUpClick // on back button back press
 * @param modifier
 * @param isShowBackButton // this variable is for back button visibility
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarComponent(
    modifier: Modifier = Modifier,
    title: String,
    isShowBackButton: Boolean,
    onUpClick: () -> Unit,
) {
    Log.d("Mayuresh=> ", "button " + isShowBackButton)
    TopAppBar(
        title = {
            Text(
                text = title,
                style = EuropeCountryTypography.labelMedium,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = modifier.statusBarsPadding(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
        ),
        navigationIcon = {
            if (isShowBackButton) {
                IconButton(onClick = { onUpClick.invoke() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
    )
}
