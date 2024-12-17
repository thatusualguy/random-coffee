package dev.suai.randomcoffee.ui.meets.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.entity.Meet
import dev.suai.randomcoffee.ui.components.Header
import dev.suai.randomcoffee.ui.components.StyledTextButton
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date

@Parcelize
data object HistoryScreen : Screen {
    data class State(
        val history: List<Meet>,
        val loading: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class HistoryClicked(val tgHandle: String) : Event()
    }
}

@SuppressLint("SimpleDateFormat")
@CircuitInject(HistoryScreen::class, SingletonComponent::class)
@Composable
fun History(
    state: HistoryScreen.State,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("История", style = MaterialTheme.typography.titleLarge, fontSize = 30.sp)

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.history) { historyMeet ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        val dateString =
                            SimpleDateFormat("dd.MM").format(historyMeet.date ?: Date())
                        Text(dateString, style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)

                        Text(historyMeet.name, style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)

                        StyledTextButton(text = "@${historyMeet.tg}",
                            onClick = {
                                state.eventSink(HistoryScreen.Event.HistoryClicked(historyMeet.tg))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHistory() {
    val state = HistoryScreen.State(
        history = (1..10).map { Meet(Date(2024, 11, it), "Максим", "thatusualguy") },
        loading = false,
        eventSink = { }
    )

    RandomCoffeeTheme {
        Column {
            Header()
            History(state, modifier = Modifier.fillMaxSize())
        }
    }
}