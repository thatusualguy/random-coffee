package dev.suai.randomcoffee.ui

import androidx.compose.runtime.Composable
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.suai.randomcoffee.ui.auth.login.LoginScreen
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

@Composable
fun RandomCoffeeApp(circuit: Circuit) {

    val backStack = rememberSaveableBackStack(root = LoginScreen)
    val navigator = rememberCircuitNavigator(backStack)
    RandomCoffeeTheme {
        CircuitCompositionLocals(circuit) {
            NavigableCircuitContent(navigator, backStack)
        }
    }
}