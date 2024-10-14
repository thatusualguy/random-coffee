package dev.suai.randomcoffee.ui.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import dev.suai.randomcoffee.ui.auth.login.LoginScreen
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.HaveAccountClicked
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.LoginChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.NameChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.PasswordChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.TgHandleChanged
import dev.suai.randomcoffee.ui.components.LoginInput
import dev.suai.randomcoffee.ui.components.PasswordInput
import dev.suai.randomcoffee.ui.components.StringInput
import kotlinx.parcelize.Parcelize

@Parcelize
data object RegisterScreen : Screen  {
    data class State(
        val login: String,
        val password: String,
        val tgHandle: String,
        val name: String,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class LoginChanged(val login: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data class TgHandleChanged(val tgHandle: String) : Event()
        data class NameChanged(val name: String) : Event()

        data class HaveAccountClicked(
            val login: String, val password: String
        ) : Event()

        data class RegisterClicked(
            val login: String, val password: String,
            val tgHandle: String, val name: String
        ) : Event()
    }
}

@CircuitInject(RegisterScreen::class, SingletonComponent::class)
@Composable
fun Register(state: RegisterScreen.State, modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            LoginInput(
                value = state.login,
                onValueChange = { state.eventSink(LoginChanged(it)) }
            )

            PasswordInput(
                value = state.password,
                onValueChange = { state.eventSink(PasswordChanged(it)) }
            )

            StringInput(
                value = state.tgHandle,
                onValueChange = { state.eventSink(TgHandleChanged(it)) },
                label = "Телеграмм",
                placeholder = "@thatusualguy"
            )

            StringInput(
                value = state.name,
                onValueChange = { state.eventSink(NameChanged(it)) },
                label = "Имя",
                placeholder = "Максим"
            )

            Spacer(Modifier.height(64.dp))

            TextButton(
                onClick = { state.eventSink(HaveAccountClicked(state.login, state.password)) }) {
                Text("есть аккаунта")
            }
        }

    }
}