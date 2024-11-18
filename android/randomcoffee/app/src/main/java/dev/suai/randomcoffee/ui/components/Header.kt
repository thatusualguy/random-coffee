package dev.suai.randomcoffee.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.suai.randomcoffee.R

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Image(
        painterResource(R.drawable.frame),
        null,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun PreviewHeader() {
    Header()
}