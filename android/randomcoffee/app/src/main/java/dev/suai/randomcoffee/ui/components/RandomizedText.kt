package dev.suai.randomcoffee.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

private val DefaultReplacements = ('A'..'Z').toList() + ('0'..'9').toList()

@Composable
fun RandomizedText(
    text: String,
    timeoutMs: Int = 500,
    replacements: Int = 1,
    replaceWith: List<Char> = DefaultReplacements,
    content: @Composable (String) -> Unit
) {
    var mangledText by remember { mutableStateOf(text) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(text) {
        scope.launch {
            while (true) {
                val randomIndex = Random.nextInt(replaceWith.size)
                val randomChar = replaceWith[randomIndex]

                val sb = StringBuilder(text)
                var replaceIndex = Random.nextInt(text.length)
                if (sb[replaceIndex].isWhitespace())
                    replaceIndex++
                sb.setCharAt(replaceIndex, randomChar)

                mangledText = sb.toString()
                delay(timeoutMs.toLong())
            }
        }
    }

    content(mangledText)
}