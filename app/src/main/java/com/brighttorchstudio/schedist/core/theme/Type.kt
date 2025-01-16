package com.brighttorchstudio.schedist.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.brighttorchstudio.schedist.R


val lexendDecaFamily = FontFamily(
    Font(R.font.lexenddeca_regular, FontWeight.Normal),
    Font(R.font.lexenddeca_bold, FontWeight.Bold),
    Font(R.font.lexenddeca_black, FontWeight.Black),
    Font(R.font.lexenddeca_extrabold, FontWeight.ExtraBold),
    Font(R.font.lexenddeca_extralight, FontWeight.ExtraLight),
    Font(R.font.lexenddeca_light, FontWeight.Light),
    Font(R.font.lexenddeca_medium, FontWeight.Medium),
    Font(R.font.lexenddeca_semibold, FontWeight.SemiBold),
    Font(R.font.lexenddeca_thin, FontWeight.Thin),
)
val AppTypography = Typography().copy(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = lexendDecaFamily,
    ),
    displayMedium = Typography().displayMedium.copy(
        fontFamily = lexendDecaFamily,
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = lexendDecaFamily,
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = lexendDecaFamily,
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = lexendDecaFamily,
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = lexendDecaFamily,
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = lexendDecaFamily,
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = lexendDecaFamily,
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = lexendDecaFamily,
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = lexendDecaFamily,
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = lexendDecaFamily,
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = lexendDecaFamily,
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = lexendDecaFamily,
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = lexendDecaFamily,
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = lexendDecaFamily,
    ),
)
