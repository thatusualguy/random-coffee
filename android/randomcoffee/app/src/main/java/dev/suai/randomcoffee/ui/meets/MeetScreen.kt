package dev.suai.randomcoffee.ui.meets

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
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

//    Scaffold(
////        bottomBar = MyBottomBar()
//    )

        Text("meet")

}


@Composable
@Preview
private fun MeetPreview() {

    val state = MeetScreen.State({})

    RandomCoffeeTheme {
        Meet(state)
    }
}