package dev.suai.randomcoffee.ui.auth.login

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
import dev.suai.randomcoffee.di.AppModule
import dev.suai.randomcoffee.di.CircuitModule
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.ui.auth.register.RegisterScreen
import dev.suai.randomcoffee.ui.meets.MeetScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginPresenter
@AssistedInject
constructor(
    @Assisted private val navigator: Navigator,
    private val authRepository: AuthRepository
) : Presenter<LoginScreen.State> {
    @Composable
    override fun present(): LoginScreen.State {
        val scope = rememberCoroutineScope()

        var login by rememberRetained { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        return LoginScreen.State(login, password) { event ->
            when (event) {
                LoginScreen.Event.ForgotPasswordClicked -> {}
                is LoginScreen.Event.LoginChanged -> login = event.login
                is LoginScreen.Event.LoginClicked -> {

                    scope.launch(Dispatchers.IO) {
                        val authResult = authRepository.authenticate(login, password)

                        when (authResult) {
                            is AuthResult.Error -> TODO()
                            is AuthResult.NetworkError -> TODO()
                            is AuthResult.Success -> navigator.goTo(MeetScreen)
                            AuthResult.WrongLogin -> TODO()
                            AuthResult.WrongPassword -> TODO()
                        }

                    }.invokeOnCompletion {

                    }


                }
                LoginScreen.Event.NoAccountClicked -> navigator.goTo(RegisterScreen)
                is LoginScreen.Event.PasswordChanged -> password = event.password
            }
        }
    }

    @CircuitInject(LoginScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(navigator: Navigator): LoginPresenter
    }
}