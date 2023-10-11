package com.mayuresh.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.mayuresh.common.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
)

val defaultTextStyle = TextStyle(
    fontFamily = Montserrat,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false,
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

val EuropeCountryTypography = Typography(

    labelLarge = defaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelMedium = defaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = defaultTextStyle.copy(
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    )
)
