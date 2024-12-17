package dev.suai.randomcoffee.ui.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.ui.components.AppName
import dev.suai.randomcoffee.ui.components.BasicButton
import dev.suai.randomcoffee.ui.components.EmailInput
import dev.suai.randomcoffee.ui.components.Header
import dev.suai.randomcoffee.ui.components.PasswordInput
import dev.suai.randomcoffee.ui.components.StyledTextButton
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
        data object ErrorShown : Event()
        data object ForgotPasswordClicked : Event()
        data object NoAccountClicked : Event()
    }
}

@CircuitInject(LoginScreen::class, SingletonComponent::class)
@Composable
fun Login(state: LoginScreen.State, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            state.eventSink(LoginScreen.Event.ErrorShown)
        }
    }


    Column(modifier = modifier) {
        Header()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AppName(Modifier.fillMaxWidth())

            Column() {
                EmailInput(
                    value = state.login,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { state.eventSink(LoginScreen.Event.LoginChanged(it)) }
                )

                PasswordInput(
                    value = state.password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { state.eventSink(LoginScreen.Event.PasswordChanged(it)) }
                )
            }


            Column() {
                StyledTextButton(
                    "нет аккаунта",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.eventSink(LoginScreen.Event.NoAccountClicked)
                }

                Spacer(Modifier.height(10.dp))

                BasicButton(
                    "Войти",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !state.loading,
                    onClick = {
                        state.eventSink(
                            LoginScreen.Event.LoginClicked(state.login, state.password)
                        )
                    },
                )
            }
        }
    }
}


@Composable
@Preview
private fun LoginPreview() {
    val state = LoginScreen.State(login = "", password = "", eventSink = {})

    Box(Modifier.padding(vertical = 10.dp)) {
        RandomCoffeeTheme {
            Login(state)
        }
    }
}