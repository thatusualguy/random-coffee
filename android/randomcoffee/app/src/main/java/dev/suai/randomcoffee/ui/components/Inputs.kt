package dev.suai.randomcoffee.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme


@Composable
fun EmailInput(
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier = Modifier
) {
    StringInput(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = "Эл.почта",
        placeholder = "example@example.com",
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
        isError = errorText != null,
        supportingText = { errorText?.let { Text(it) } },
    )
}

@Composable
@Preview
private fun LoginInputPreview() {
    RandomCoffeeTheme {
        Column {
            EmailInput(
                "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            EmailInput(
                "aaa",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            PasswordInput(
                "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            PasswordInput(
                "aaa",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    modifier: Modifier = Modifier
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }


    StringInput(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = "Пароль",
        placeholder = "abcd1234",
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
        isError = errorText != null,
        supportingText = { errorText?.let { Text(it) } },
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, null, tint = MaterialTheme.colorScheme.outline)
            }
        }
    )

}

@Composable
fun StringInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.alpha(0.6f)
            )
        },
        leadingIcon = leadingIcon,
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        singleLine = true,
        textStyle = MaterialTheme.typography.labelLarge,
        colors = OutlinedTextFieldDefaults.colors().copy(
//            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,

        )
    )
}


@Preview
@Composable
private fun PreviewStringInput() {
    RandomCoffeeTheme {
        StringInput("aaa", onValueChange = {})
    }
}