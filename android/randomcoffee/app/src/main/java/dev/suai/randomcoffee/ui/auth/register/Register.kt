package dev.suai.randomcoffee.ui.auth.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.di.AppModule
import dev.suai.randomcoffee.di.CircuitModule
import dev.suai.randomcoffee.ui.auth.login.LoginScreen

@CircuitInject(RegisterScreen::class, SingletonComponent::class)
class RegisterPresenter(
    private val navigator: Navigator,
//    private val accountRepository: AccountRepository
) : Presenter<RegisterScreen.State> {
    @Composable
    override fun present(): RegisterScreen.State {
        var login by rememberRetained { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var tgHandle by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }


        return RegisterScreen.State(login, password, tgHandle, name) { event ->
            when (event) {
                is RegisterScreen.Event.LoginChanged -> login = event.login
                is RegisterScreen.Event.NameChanged -> name = event.name
                is RegisterScreen.Event.PasswordChanged -> password = event.password
                is RegisterScreen.Event.TgHandleChanged -> tgHandle = event.tgHandle
                is RegisterScreen.Event.HaveAccountClicked -> navigator.goTo(LoginScreen)
                is RegisterScreen.Event.RegisterClicked -> TODO()
            }
        }
    }
}