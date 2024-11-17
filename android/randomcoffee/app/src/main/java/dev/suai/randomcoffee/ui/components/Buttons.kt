package dev.suai.randomcoffee.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

@Composable
fun BasicButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {

    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(text.lowercase())
    }
}


@Composable
@Preview
fun ButtonPreview() {
    RandomCoffeeTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            BasicButton("Регистрация") { }
            BasicButton("Войти") { }
        }
    }
}