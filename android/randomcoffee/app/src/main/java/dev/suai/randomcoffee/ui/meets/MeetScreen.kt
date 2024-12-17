package dev.suai.randomcoffee.ui.meets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.ui.globalCircuit
import dev.suai.randomcoffee.ui.meets.calendar.MeetsCalendarScreen
import dev.suai.randomcoffee.ui.meets.future.FutureMeetsScreen
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data object MeetScreen : Screen {
    data class State(
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class Bar(val fiz: Int) : Event()
    }
}


@CircuitInject(MeetScreen::class, SingletonComponent::class)
@Composable
fun Meet(state: MeetScreen.State, modifier: Modifier = Modifier) {

    val upperStack = rememberSaveableBackStack(root = MeetsCalendarScreen)
    val upperNavigator = rememberCircuitNavigator(upperStack)

    val lowerStack = rememberSaveableBackStack(root = FutureMeetsScreen)
    val lowerNavigator = rememberCircuitNavigator(lowerStack)

    Column(modifier.fillMaxWidth()) {
        globalCircuit?.let {
            CircuitCompositionLocals(it) {
                NavigableCircuitContent(upperNavigator, upperStack)
                NavigableCircuitContent(lowerNavigator, lowerStack)
            }
        }
    }
}


@Composable
@Preview
private fun MeetPreview() {

    val state = MeetScreen.State {}

    RandomCoffeeTheme {
        Meet(state)
    }
}