package dev.suai.randomcoffee.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object LoginScreen : Screen {
    data class State(
        val login: String,
        val password: String,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class LoginChanged(val login: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data class LoginClicked(val login: String, val password: String) : Event()
        data object ForgotPasswordClicked : Event()
        data object NoAccountClicked : Event()
    }
}


@Composable
fun Login(state: LoginScreen.State, modifier: Modifier = Modifier) {

}


class LoginPresenter(
    private val navigator: Navigator,
//    private val accountRepository: AccountRepository
) : Presenter<LoginScreen.State> {
    @Composable
    override fun present(): LoginScreen.State {
        var login by rememberRetained { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


//        fun validate

        return LoginScreen.State(login, password) { event ->
            when (event) {
                LoginScreen.Event.ForgotPasswordClicked -> TODO()
                is LoginScreen.Event.LoginChanged -> login = event.login
                is LoginScreen.Event.LoginClicked -> TODO()
                LoginScreen.Event.NoAccountClicked -> navigator.goTo(RegisterScreen)
                is LoginScreen.Event.PasswordChanged -> password = event.password
            }
        }


    }
}

