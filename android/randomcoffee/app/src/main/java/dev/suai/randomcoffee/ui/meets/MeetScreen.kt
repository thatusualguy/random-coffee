package dev.suai.randomcoffee.ui.meets

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.suai.randomcoffee.ui.auth.login.LoginScreen.Event
import kotlinx.parcelize.Parcelize

@Parcelize
data object MeetScreen : Screen {
    data class State(
        val foo: Int,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class Bar(val fiz: Int) : Event()
    }
}