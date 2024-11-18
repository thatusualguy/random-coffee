package dev.suai.randomcoffee.ui.interests

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestGroup
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data object InterestsScreen : Screen {
    data class State(
        val allInterests: List<InterestGroup> = emptyList(),
        val selectedInterests: Set<Interest>,
        val startSelectedInterests: Set<Interest>,
        val hasChanges: Boolean = false,

        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class InterestClicked(val interest: Interest) : Event()
        data object ApplyClicked : Event()
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Interests(
    state: InterestsScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(floatingActionButton =
    {
        AnimatedVisibility(
            visible = state.hasChanges,
            enter = expandIn { IntSize(width = 1, height = 1) } + fadeIn()
        ) {
            FloatingActionButton(
                onClick = { state.eventSink(InterestsScreen.Event.ApplyClicked) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    "Сохранить",
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }) { _ ->

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(30.dp)) {
            state.allInterests.forEach { group ->
                Column() {
                    Text(
                        group.name,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        modifier = Modifier
                            .padding(7.dp)
                    ) {
                        group.interests.forEach { interest ->
                            val isSelected = interest in state.selectedInterests
                            InputChip(
                                isSelected,
                                onClick = {
                                    state.eventSink(
                                        InterestsScreen.Event.InterestClicked(
                                            interest
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        interest.name,
                                        modifier = Modifier
                                            //                            .background(color = Color.Gray, shape = CircleShape)
                                            .padding(vertical = 3.dp, horizontal = 5.dp)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewInterests() {
    val state = InterestsScreen.State(
        allInterests = listOf(
            InterestGroup(
                "Технологии",
                listOf(
                    Interest("Веб", 1),
                    Interest("БД", 2),
                )
            ),
            InterestGroup(
                "Спорт",
                listOf(
                    Interest("Веб", 2),
                    Interest("БД", 3),
                )
            ),
        ),
        eventSink = { },
        selectedInterests = setOf(Interest("Веб", 1)),
        startSelectedInterests = setOf(Interest("Веб", 1)),
        hasChanges = true,
    )

    RandomCoffeeTheme {
        Interests(state)
    }
}