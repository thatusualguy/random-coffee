package dev.suai.randomcoffee.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import dev.suai.randomcoffee.ui.theme.geologicaFontFamily

@Composable
fun AppName(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(-25.dp)
    ) {
        RandomizedText(
            "random coffee".uppercase(),
            replaceWith = ('0'..'9').toList(),
            replacements = 2,
            timeoutMs = 500,
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                it.split(" ").first().forEach {
                    Text(
                        it.toString(),
                        maxLines = 1,
                        fontFamily = geologicaFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 70.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .weight(1f),
                        lineHeight = 2.sp
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                it.split(" ").last().forEach {
                    Text(
                        it.toString(),
                        maxLines = 1,
                        fontFamily = geologicaFontFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 78.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.weight(1f),
                        lineHeight = 2.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAppName() {
    RandomCoffeeTheme {
        Column(Modifier.fillMaxSize()) {
            Header()
            Spacer(Modifier.weight(1f))
            AppName()
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.weight(1f))
        }
    }
}