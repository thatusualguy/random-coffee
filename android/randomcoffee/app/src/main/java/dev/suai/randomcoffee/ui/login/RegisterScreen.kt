package dev.suai.randomcoffee.ui.login

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object RegisterScreen : Screen {
    data class State(
        val login: String,
        val password: String,
        val tgHandle: String,
        val name: String,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {

    }
}