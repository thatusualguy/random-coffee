package dev.suai.randomcoffee.ui.auth.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.entity.RegisterResult
import dev.suai.randomcoffee.ui.auth.login.LoginScreen
import dev.suai.randomcoffee.ui.menu.MainCombinationScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val authRepository: AuthRepository
) : Presenter<RegisterScreen.State> {
    @Composable
    override fun present(): RegisterScreen.State {
        val scope = rememberCoroutineScope()

        var login by rememberRetained { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var tgHandle by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var eula by remember { mutableStateOf(false) }
        var loading by remember { mutableStateOf(false) }
        var error by remember { mutableStateOf<String?>(null) }

        return RegisterScreen.State(
            login = login,
            password = password,
            tgHandle = tgHandle,
            name = name,
            loading = loading,
            eula = eula,
            error = error,
            eventSink = { event ->
                when (event) {
                    is RegisterScreen.Event.LoginChanged -> login = event.login
                    is RegisterScreen.Event.NameChanged -> name = event.name
                    is RegisterScreen.Event.PasswordChanged -> password = event.password
                    is RegisterScreen.Event.TgHandleChanged -> tgHandle = event.tgHandle
                    is RegisterScreen.Event.HaveAccountClicked -> navigator.goTo(LoginScreen)
                    is RegisterScreen.Event.EulaChanged -> eula = event.state
                    RegisterScreen.Event.ErrorShown -> error = null
                    is RegisterScreen.Event.RegisterClicked -> {
                        scope.launch(Dispatchers.IO) {
                            val authResult = authRepository.register(
                                login = event.login,
                                password = event.password,
                                tgHandle = event.tgHandle,
                                name = event.name
                            )
                            loading = true
                            when (authResult) {
                                is RegisterResult.Error -> error = authResult.msg
                                is RegisterResult.NetworkError -> error = authResult.msg
                                RegisterResult.Success -> navigator.goTo(MainCombinationScreen)
                            }
                        }.invokeOnCompletion {
                            loading = false
                        }
                    }
                }
            }
        )
    }

    @CircuitInject(RegisterScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): RegisterPresenter
    }
}