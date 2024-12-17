package dev.suai.randomcoffee.ui.meets.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.ui.theme.Brown
import dev.suai.randomcoffee.ui.theme.DarkBrown
import dev.suai.randomcoffee.ui.theme.Dunno
import dev.suai.randomcoffee.ui.theme.Orange
import dev.suai.randomcoffee.ui.theme.OrangeLight
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Parcelize
data object MeetsCalendarScreen : Screen {
    data class State(
        val plannedMeets: List<LocalDate>,
        val loading: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class CalendarClick(val date: LocalDate) : Event()
    }
}

@CircuitInject(MeetsCalendarScreen::class, SingletonComponent::class)
@Composable
fun MeetsCalendar(
    state: MeetsCalendarScreen.State,
    modifier: Modifier = Modifier
) {

    val calendarState = rememberSelectableCalendarState(
        initialMonth = YearMonth.now(),
        minMonth = YearMonth.now(),
        initialSelectionMode = SelectionMode.Multiple,
        initialSelection = state.plannedMeets,
        confirmSelectionChange = {
            state.eventSink(MeetsCalendarScreen.Event.CalendarClick(it.first()))
            true
        }
    )


    LaunchedEffect(state.plannedMeets) {
        calendarState.selectionState.selection = state.plannedMeets
    }

    Box() {
        SelectableCalendar(
            modifier = Modifier.fillMaxWidth(),
            firstDayOfWeek = DayOfWeek.MONDAY,
            calendarState = calendarState,
            dayContent = { dayState ->
                val date = dayState.date
                val isSelected = date in state.plannedMeets
                val hasPassed = date.isBefore(LocalDate.now())

                val containerColor = if (hasPassed) Dunno else if (isSelected) DarkBrown else Orange

                Card(
                    modifier = modifier
                        .aspectRatio(1f)
                        .padding(3.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (dayState.isFromCurrentMonth) 8.dp else 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = containerColor,
                        contentColor = OrangeLight,
                        disabledContainerColor = Dunno,
                        disabledContentColor = Brown
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .clickable(enabled = !hasPassed) {
//                                state.eventSink(MeetsCalendarScreen.Event.CalendarClick(date))
                            }
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = date.dayOfMonth.toString())
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun FutureMeetsPreview() {
    val state = MeetsCalendarScreen.State(
        plannedMeets = listOf(
            LocalDate.now().minusDays(1),
            LocalDate.now().minusDays(2),
            LocalDate.now().plusDays(5),
            LocalDate.now().plusDays(6),
        ),
        loading = false,
        eventSink = { }
    )

    RandomCoffeeTheme {
        Column(Modifier.fillMaxSize()) {
            MeetsCalendar(state)
        }
    }
}