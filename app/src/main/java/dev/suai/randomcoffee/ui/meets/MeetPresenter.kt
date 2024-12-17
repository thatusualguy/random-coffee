package dev.suai.randomcoffee.ui.meets

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent

class MeetPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
//    private val authRepository: AuthRepository
) : Presenter<MeetScreen.State> {

    @Composable
    override fun present(): MeetScreen.State {
        return MeetScreen.State {  }
    }


    @CircuitInject(MeetScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): MeetPresenter
    }
}