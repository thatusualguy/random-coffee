package dev.suai.randomcoffee.ui.interests

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
import dev.suai.randomcoffee.domain.InterestsRepository
import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InterestsPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val interestsRepository: InterestsRepository
) : Presenter<InterestsScreen.State> {
    @Composable
    override fun present(): InterestsScreen.State {
        val scope = rememberCoroutineScope()


        var allInterests: List<InterestGroup> by remember { mutableStateOf(emptyList()) }
        var selectedInterests: Set<Interest> by remember { mutableStateOf(emptySet()) }
        var startSelectedInterests: Set<Interest> by remember { mutableStateOf(emptySet()) }


        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                val res = interestsRepository.getInterests()
                allInterests = res.allInterests
                startSelectedInterests = res.selectedInterests.toSet()
                selectedInterests = startSelectedInterests
            }
        }

        val hasChanges = selectedInterests != startSelectedInterests

        return InterestsScreen.State(
            allInterests,
            selectedInterests,
            startSelectedInterests,
            hasChanges
        ) { event ->
            when (event) {
                InterestsScreen.Event.ApplyClicked ->
                    scope.launch(Dispatchers.IO) {
                        interestsRepository.saveInterests()
                    }.invokeOnCompletion {
                        startSelectedInterests = selectedInterests
                    }

                is InterestsScreen.Event.InterestClicked -> interestsRepository.toggleInterest(event.interest)
            }
        }
    }

    @CircuitInject(InterestsScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): InterestsPresenter
    }
}