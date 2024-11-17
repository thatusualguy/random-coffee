package dev.suai.randomcoffee.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.ui.globalCircuit
import dev.suai.randomcoffee.ui.meets.MeetScreen
import dev.suai.randomcoffee.ui.menu.navbar.NavBarScreen
import kotlinx.parcelize.Parcelize

@Parcelize
data object MainCombinationScreen : Screen {
    object State : CircuitUiState
}


@CircuitInject(MainCombinationScreen::class, SingletonComponent::class)
@Composable
fun MainCombination(modifier: Modifier) {
    val mainBackStack = rememberSaveableBackStack(root = MeetScreen)
    val mainNavigator = rememberCircuitNavigator(mainBackStack)

    val navBarStack = rememberSaveableBackStack(root = NavBarScreen)

    Column(Modifier.fillMaxSize()) {
        globalCircuit?.let {
            CircuitCompositionLocals(it) {
                NavigableCircuitContent(mainNavigator, mainBackStack)
                Spacer(Modifier.weight(1f))
                NavigableCircuitContent(mainNavigator, navBarStack)
            }
        }
    }
}

@CircuitInject(MainCombinationScreen::class, SingletonComponent::class)
@Composable
fun mainCombinationPresenter(navigator: Navigator): MainCombinationScreen.State {
    navigator.resetRoot(MainCombinationScreen)

    return MainCombinationScreen.State
}
