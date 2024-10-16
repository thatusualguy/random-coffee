package dev.suai.randomcoffee.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import dev.suai.randomcoffee.ui.components.LoginInput
import dev.suai.randomcoffee.ui.components.PasswordInput
import dev.suai.randomcoffee.ui.components.RandomizedText
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Spacer(Modifier.weight(1f))

            RandomizedText("Random Coffee") {
                Text(it, fontSize = 40.sp, letterSpacing = 5.sp)
            }
            Spacer(Modifier.weight(2f))

            LoginInput(
                value = state.login,
                onValueChange = { state.eventSink(LoginScreen.Event.LoginChanged(it)) }
            )
            Column {

                PasswordInput(
                    value = state.password,
                    onValueChange = { state.eventSink(LoginScreen.Event.PasswordChanged(it)) }
                )

                TextButton(
                    onClick = { state.eventSink(LoginScreen.Event.ForgotPasswordClicked) },
                    modifier = Modifier.padding(top = (0).dp)
                ) {
                    Text("восстановить пароль")
                }
            }

            Spacer(Modifier.weight(1f))


            Column(
                Modifier.fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = { state.eventSink(LoginScreen.Event.NoAccountClicked) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("нет аккаунта")
                }

                Button(
                    onClick = {
                        state.eventSink(
                            LoginScreen.Event.LoginClicked(state.login, state.password)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Войти")
                }
            }
        }

    }
}


@Composable
@Preview
private fun LoginPreview() {
    val state = LoginScreen.State(login = "", password = "", eventSink = {})

    RandomCoffeeTheme {
        Login(state)
    }
}