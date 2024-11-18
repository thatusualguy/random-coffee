package dev.suai.randomcoffee.ui.theme

import androidx.compose.foundation.MutatePriority
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.suai.randomcoffee.R


val geologicaFontFamily = FontFamily(
    Font(R.font.geologica_auto_light, FontWeight.Light),
    Font(R.font.geologica_auto_semibold, FontWeight.SemiBold),
    Font(R.font.geologica_auto_regular, FontWeight.Normal),
    Font(R.font.geologica_auto_regular, FontWeight.Normal),
    Font(R.font.geologica_auto_medium, FontWeight.Medium),
    Font(R.font.geologica_auto_bold, FontWeight.Bold)
)

val vdsFontFamily = FontFamily(
    Font(R.font.vds_new, FontWeight.Normal)
)

private val defaultTypography = Typography()
val Typography = Typography(
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = geologicaFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = geologicaFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = geologicaFontFamily),

    titleLarge     = defaultTypography.titleLarge.copy(fontFamily = geologicaFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = geologicaFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = geologicaFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = geologicaFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = geologicaFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = geologicaFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = geologicaFontFamily, fontSize = 25.sp),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = vdsFontFamily, fontSize = 25.sp, lineHeight = 20.sp),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = vdsFontFamily),

    displayLarge = defaultTypography.displayLarge.copy(fontFamily = geologicaFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = geologicaFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = geologicaFontFamily),
)



//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = geologicaFontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
//    titleSmall = TextStyle(
//        fontFamily = geologicaFontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 20.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
//    titleMedium = TextStyle(
//        fontFamily = geologicaFontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 25.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
//    labelMedium = TextStyle(
//        fontFamily = vdsFontFamily,
//        fontWeight = FontWeight.Normal,
//        fontSize = 28.sp,
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)