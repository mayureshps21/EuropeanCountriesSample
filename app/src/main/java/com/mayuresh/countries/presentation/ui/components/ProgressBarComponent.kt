package com.mayuresh.countries.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarComponent(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}
