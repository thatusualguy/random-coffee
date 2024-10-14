package dev.suai.randomcoffee.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import dev.suai.randomcoffee.ui.auth.login.Login
import dev.suai.randomcoffee.ui.auth.login.LoginPresenter
import dev.suai.randomcoffee.ui.auth.login.LoginScreen
import dev.suai.randomcoffee.ui.auth.register.Register
import dev.suai.randomcoffee.ui.auth.register.RegisterPresenter
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

@Composable
fun RandomCoffeeApp() {

    RandomCoffeeTheme {
//        CircuitCompositionLocals(circuit) {
////            CircuitContent(AddFavoritesScreen())
//        }
    }
}