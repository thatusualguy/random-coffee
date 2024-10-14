package dev.suai.randomcoffee.ui.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.di.AppModule
import dev.suai.randomcoffee.di.CircuitModule
import dev.suai.randomcoffee.ui.components.LoginInput
import dev.suai.randomcoffee.ui.components.PasswordInput
import kotlinx.parcelize.Parcelize

@Parcelize
data object LoginScreen : Screen {
    data class State(
        val login: String,
        val password: String,
        val loading: Boolean = false,
        val error: String? = null,
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

@CircuitInject(LoginScreen::class, SingletonComponent::class)
@Composable
fun Login(state: LoginScreen.State, modifier: Modifier = Modifier) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            LoginInput(
                value = state.login,
                onValueChange = { state.eventSink(LoginScreen.Event.LoginChanged(it)) }
            )

            PasswordInput(
                value = state.password,
                onValueChange = { state.eventSink(LoginScreen.Event.PasswordChanged(it)) }
            )

            TextButton(
                onClick = { state.eventSink(LoginScreen.Event.ForgotPasswordClicked) }) {
                Text("забыл пароль")
            }

            Spacer(Modifier.height(64.dp))

            TextButton(
                onClick = { state.eventSink(LoginScreen.Event.NoAccountClicked) }) {
                Text("нет аккаунта")
            }

            Button(
                onClick = {
                    state.eventSink(
                        LoginScreen.Event.LoginClicked(
                            state.login,
                            state.password
                        )
                    )
                }
            ) {
                Text("Войти")
            }
        }

    }
}