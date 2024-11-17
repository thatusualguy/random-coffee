package dev.suai.randomcoffee.ui.interests

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object InterestsScreen : Screen {
    data class State(
        val selectedIndex: Int = 0,
        val items: List<NavItem>,

        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object NavigateToProfile : Event()
        data object NavigateToMeet : Event()
        data object NavigateToInterests : Event()
    }

    data class NavItem(val action: Event, val text: String, val icon: Int)
}