package dev.suai.randomcoffee.ui

import androidx.compose.runtime.Composable
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

@Composable
fun RandomCoffeeApp(){
    RandomCoffeeTheme {
        CircuitCompositionLocals(circuit) {
//            CircuitContent(AddFavoritesScreen())
        }
    }
}