package dev.suai.randomcoffee.ui.meets.history

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.HistoryRepository
import dev.suai.randomcoffee.domain.entity.Meet
import dev.suai.randomcoffee.ui.meets.history.HistoryScreen.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val historyRepository: HistoryRepository
) : Presenter<State> {

    @Composable
    override fun present(): State {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        var history = remember { listOf<Meet>() }

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                history = historyRepository.getHistory()
            }
        }

        return State(
            history = history,
            loading = history.isEmpty(),
            eventSink = {
                when (it) {
                    is HistoryScreen.Event.HistoryClicked -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/${it.tgHandle}")
                        )
                        startActivity(context, intent, null)
                    }
                }
            }
        )
    }

    @CircuitInject(HistoryScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): HistoryPresenter
    }
}