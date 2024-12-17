package dev.suai.randomcoffee.ui.meets.future

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.entity.FutureMeet
import dev.suai.randomcoffee.ui.components.BasicButton
import dev.suai.randomcoffee.ui.components.StyledTextButton
import dev.suai.randomcoffee.ui.meets.future.FutureMeetsScreen.Event.ApproveEvent
import dev.suai.randomcoffee.ui.meets.future.FutureMeetsScreen.Event.NavigateToTg
import dev.suai.randomcoffee.ui.theme.Brown
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import kotlinx.parcelize.Parcelize


@Parcelize
data object FutureMeetsScreen : Screen {
    data class State(
        val futureMeet: FutureMeet?,
        val loading: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class ApproveEvent(val id: Int) : Event()
        data class NavigateToTg(val tgHandle: String) : Event()
    }
}


private val daysOfWeek =
    "Ошибка Понедельник Вторник Среда Четверг Пятница Суббота Воскресенье".split(" ")

@CircuitInject(FutureMeetsScreen::class, SingletonComponent::class)
@Composable
fun FutureMeets(
    state: FutureMeetsScreen.State,
    modifier: Modifier = Modifier
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {


        if (state.futureMeet == null) {
            Text("Собеседник еще не найден :(")
        } else {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Ваш собеседник: ${state.futureMeet.secondName}")

                StyledTextButton(
                    "@${state.futureMeet.secondTg}",
                    onClick = { state.eventSink(NavigateToTg(state.futureMeet.secondTg)) })
            }

            HorizontalDivider(Modifier.fillMaxWidth(), color = Brown)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${state.futureMeet.date.dayOfMonth}.${state.futureMeet.date.monthValue}")
                Text(daysOfWeek[state.futureMeet.date.dayOfWeek?.value ?: 0])
                Text("Вечер")
            }

            if (!state.futureMeet.approved)
                BasicButton("Подтвердить", modifier = Modifier.fillMaxWidth()) {
                    state.eventSink(ApproveEvent(state.futureMeet.id))
                }

        }
    }

}

@Preview
@Composable
private fun FutureMeetsPreview() {
    val state = FutureMeetsScreen.State(
        futureMeet = TODO(),
        loading = TODO(),
        eventSink = TODO()
    )

    RandomCoffeeTheme {
        Column(Modifier.fillMaxSize()) {
            FutureMeets(state)
        }
    }
}
