package dev.suai.randomcoffee.ui.profile

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object ProfileScreen : Screen {
    data class State(
        val a: Int = 0,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object NavigateToProfile : Event()
        data object NavigateToMeet : Event()
        data object NavigateToInterests : Event()
    }
}