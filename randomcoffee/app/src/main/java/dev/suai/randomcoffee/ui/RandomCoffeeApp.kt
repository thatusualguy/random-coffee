package dev.suai.randomcoffee.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.suai.randomcoffee.ui.auth.login.LoginScreen
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

var globalCircuit: Circuit? = null

@Composable
fun RandomCoffeeApp(circuit: Circuit) {
    val backStack = rememberSaveableBackStack(root = LoginScreen)
    val navigator = rememberCircuitNavigator(backStack)

    LaunchedEffect(Unit) {
        globalCircuit = circuit
    }

    RandomCoffeeTheme {
        Scaffold { padding ->
            CircuitCompositionLocals(circuit) {
                NavigableCircuitContent(navigator, backStack, modifier = Modifier.padding(padding))
            }
        }
    }
}