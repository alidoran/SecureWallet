package ir.dorantech.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.dorantech.ui.state.UIState
import ir.dorantech.viewmodel.SignInViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onForgetPasswordClicked: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val vm by localDI().instance<SignInViewModel>()
    val userState = vm.signInState.collectAsState()
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 64.dp),
    ) {
        TextFieldWithError(
            onFocusChanged = { name, isFocused ->
                if (!isFocused) vm.onNameFocusChanged(name)
            },
            errorListener = vm.nameError,
            label = "User Name",
        )

        TextFieldWithError(
            onFocusChanged = { password, isFocused ->
                if (!isFocused) vm.onPasswordFocusChanged(password)
            },
            errorListener = vm.passwordError,
            label = "Password",
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = {vm.onSendClicked()},
            modifier = Modifier.align(CenterHorizontally),
        ) {
            Text(text = "SignIn")
        }

        if (onForgetPasswordClicked != null) {
            Text(
                text = buildAnnotatedString {
                    append("Forgot Password? ")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("Click here")
                    }
                },
                modifier = Modifier.clickable {
                    onForgetPasswordClicked()
                }
            )
        }


            when(val state = userState.value) {
                is UIState.Error -> {
                    getToastHandler(ToastDuration.Short).showToast(state.message)}
                UIState.Idle -> {}
                UIState.Loading -> getToastHandler(ToastDuration.Short).showToast("Loading")
                is UIState.Success -> onSignInSuccess.invoke()
            }
    }
}