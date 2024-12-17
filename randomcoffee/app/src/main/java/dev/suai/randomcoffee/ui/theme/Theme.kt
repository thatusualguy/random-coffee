package dev.suai.randomcoffee.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = Brown,
    primary = BrownLight,
    secondary = Orange,
    tertiary = OrangeLight,
)

private val LightColorScheme = lightColorScheme(
    background = OrangeLight,
    onBackground = Blackish,

    primary = BrownLight,
    onPrimary = Color.White,

    secondary = Orange,
    onSecondary = Color.White,

    tertiary = BrownLight,

    secondaryContainer = Dunno,
    onSecondaryContainer = BorderBrowm,

    outline = BorderBrowm,

    surfaceVariant = DarkBrown,
//    onSurfaceVariant = Color.White,
)

@Composable
fun RandomCoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = {
            Surface(color = colorScheme.background) {
                content.invoke()
            }
        }
    )
}