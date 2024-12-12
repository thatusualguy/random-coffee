package dev.suai.randomcoffee.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
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
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.HaveAccountClicked
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.LoginChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.NameChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.PasswordChanged
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen.Event.TgHandleChanged
import dev.suai.randomcoffee.ui.components.AppName
import dev.suai.randomcoffee.ui.components.BasicButton
import dev.suai.randomcoffee.ui.components.EmailInput
import dev.suai.randomcoffee.ui.components.Header
import dev.suai.randomcoffee.ui.components.PasswordInput
import dev.suai.randomcoffee.ui.components.StringInput
import dev.suai.randomcoffee.ui.components.StyledTextButton
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data object RegisterScreen : Screen {
    data class State(
        val login: String,
        val password: String,
        val tgHandle: String,
        val name: String,
        val eula: Boolean = false,
        val error: String?,
        val eventSink: (Event) -> Unit,
        val loading: Boolean
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data class LoginChanged(val login: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data class TgHandleChanged(val tgHandle: String) : Event()
        data class NameChanged(val name: String) : Event()
        data class EulaChanged(val state: Boolean) : Event()

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
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
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
                    onValueChange = { state.eventSink(LoginChanged(it)) }
                )

                PasswordInput(
                    value = state.password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { state.eventSink(PasswordChanged(it)) }
                )

                StringInput(
                    value = state.name,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { state.eventSink(NameChanged(it)) },
                    placeholder = "Имя",
                    label = "Имя"
                )

                StringInput(
                    value = state.tgHandle,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { state.eventSink(TgHandleChanged(it)) },
                    placeholder = "@durov",
                    label = "Ссылка на ТГ"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Checkbox(
                        checked = state.eula,
                        onCheckedChange = { state.eventSink(RegisterScreen.Event.EulaChanged(it)) }
                    )

                    Text(
                        "Согласен с условиями использования"
                    )
                }

            }


            Column() {
                StyledTextButton(
                    "есть аккаунт",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.eventSink(HaveAccountClicked(state.login, state.password))
                }

                Spacer(Modifier.height(10.dp))

                BasicButton(
                    "Зарегистрироваться",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !state.loading,
                    onClick = {
                        state.eventSink(
                            RegisterScreen.Event.RegisterClicked(
                                login = state.login, password = state.password,
                                tgHandle = state.tgHandle,
                                name = state.name
                            )
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    val state =
        RegisterScreen.State(
            login = "", password = "", tgHandle = "", name = "", eventSink = {},
            loading = false,
            eula = false,
            error = null
        )

    Box(Modifier.padding(vertical = 10.dp)) {
        RandomCoffeeTheme {
            Register(state)
        }
    }

}