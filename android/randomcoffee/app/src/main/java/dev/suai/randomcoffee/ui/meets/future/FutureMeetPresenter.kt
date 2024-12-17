package dev.suai.randomcoffee.ui.meets.future

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.FutureMeetRepository
import dev.suai.randomcoffee.domain.entity.FutureMeet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FutureMeetPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val meetRepository: FutureMeetRepository
) : Presenter<FutureMeetsScreen.State> {

    @Composable
    override fun present(): FutureMeetsScreen.State {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()


        var futureMeet: FutureMeet? by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                futureMeet = meetRepository.getFutureMeet()
            }
        }

        return FutureMeetsScreen.State(
            loading = futureMeet == null,
            futureMeet = futureMeet,
            eventSink = {
                when (it) {
                    is FutureMeetsScreen.Event.ApproveEvent -> {
                        scope.launch(Dispatchers.IO) {
                            meetRepository.approveMeet(it.id)
                            futureMeet = meetRepository.getFutureMeet()
                        }
                    }
                    is FutureMeetsScreen.Event.NavigateToTg -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/${it.tgHandle}")
                        )
                        startActivity(context, intent, null)
                    }
                }
            },
        )
    }

    @CircuitInject(FutureMeetsScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): FutureMeetPresenter
    }
}