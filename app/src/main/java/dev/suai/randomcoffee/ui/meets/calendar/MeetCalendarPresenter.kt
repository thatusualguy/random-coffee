package dev.suai.randomcoffee.ui.meets.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.CalendarRepository
import dev.suai.randomcoffee.domain.ChangeDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MeetCalendarPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val calendarRepository: CalendarRepository
) : Presenter<MeetsCalendarScreen.State> {

    @Composable
    override fun present(): MeetsCalendarScreen.State {
        val scope = rememberCoroutineScope()
        var plannedMeets: List<LocalDate> by remember { mutableStateOf(emptyList()) }

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                plannedMeets = calendarRepository.getCalendar()
            }
        }


        return MeetsCalendarScreen.State(
            plannedMeets = plannedMeets,
            loading = false
        ) { event ->
            when (event) {
                is MeetsCalendarScreen.Event.CalendarClick -> {
                    scope.launch(Dispatchers.IO) {

                        val isAlreadyIn = event.date in plannedMeets
                        val values = listOf(event.date)

                        calendarRepository.changeDateState(
                            changes = ChangeDate(
                                toAdd = if (!isAlreadyIn) values else emptyList(),
                                toDelete = if (isAlreadyIn) values else emptyList()
                            )
                        )

                        plannedMeets = calendarRepository.getCalendar()
                    }
                }
            }
        }
    }

    @CircuitInject(MeetsCalendarScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): MeetCalendarPresenter
    }
}