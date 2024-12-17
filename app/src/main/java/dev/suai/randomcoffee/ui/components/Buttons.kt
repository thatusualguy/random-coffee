package dev.suai.randomcoffee.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

@Composable
fun BasicButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
//        contentPadding = PaddingValues(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled
    ) {
        Text(
            text.lowercase(),
//            lineHeight = 10.sp,
            modifier = Modifier.padding(0.dp),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1
        )
    }
}


@Composable
fun StyledTextButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text.lowercase(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun PreviewStyledTextButton() {
    RandomCoffeeTheme {
        Column {
            StyledTextButton("нет аккаунта") {}
            StyledTextButton("нет аккаунта", Modifier.fillMaxWidth()) {}
        }
    }
}

@Composable
@Preview
fun ButtonPreview() {
    RandomCoffeeTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicButton("Регистрация", onClick = { })
            BasicButton("Войти", Modifier.fillMaxWidth(), onClick = { })
        }
    }
}