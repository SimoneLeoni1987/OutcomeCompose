package it.simo.outcomecompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import it.simo.outcomecompose.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val SportFontFamily = FontFamily(
    Font(R.font.sport_semibold, FontWeight.SemiBold), // Assuming sport_semibold is for SemiBold weight
    Font(R.font.sport_regular, FontWeight.Normal)
)

val Caption1_SemiBold = TextStyle(
    fontFamily = SportFontFamily,
    fontWeight = SemiBold,
    fontSize = 12.sp
)

val Footnote_SemiBold = TextStyle(
    fontFamily = SportFontFamily,
    fontWeight = SemiBold,
    fontSize = 13.sp
)

