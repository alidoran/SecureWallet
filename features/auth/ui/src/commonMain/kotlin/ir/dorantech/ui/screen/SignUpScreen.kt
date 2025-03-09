package ir.dorantech.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.dorantech.CircleImage
import ir.dorantech.ui.TextFieldWithError
import ir.dorantech.ui.ToastDuration
import ir.dorantech.ui.component.CustomDropdownMenu
import ir.dorantech.ui.getToastHandler
import ir.dorantech.ui.state.UIState
import ir.dorantech.viewmodel.SignUpViewModel
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vm by localDI().instance<SignUpViewModel>()
    val userState = vm.signUpState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var selectedGender by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Column(
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
        ) {
            CircleImage(url = "https://www.w3.org/MarkUp/Test/xhtml-print/20050519/tests/jpeg420exif.jpg")
        }

        TextFieldWithError(
            onFocusChanged = { username, isFocused ->
                if (!isFocused) vm.onUserNameFocusChanged(username)
            },
            errorListener = vm.userNameError,
            label = "Username",
        )

        TextFieldWithError(
            onFocusChanged = { password, isFocused ->
                if (!isFocused) vm.onPasswordFocusChanged(password)
            },
            errorListener = vm.passwordError,
            label = "Password",
            modifier = Modifier.padding(top = 8.dp)
        )

        TextFieldWithError(
            onFocusChanged = { confirmPassword, isFocused ->
                if (!isFocused) vm.onConfirmPasswordFocusChanged(confirmPassword)
            },
            errorListener = vm.passwordConfirmError,
            label = "Confirm Password",
            modifier = Modifier.padding(top = 8.dp)
        )

        TextFieldWithError(
            onFocusChanged = { firstName, isFocused ->
                if (!isFocused) vm.onFirstLastNameFocusChanged(firstName, true)
            },
            errorListener = vm.firstNameError,
            label = "First Name",
            modifier = Modifier.padding(top = 8.dp),
        )

        TextFieldWithError(
            onFocusChanged = { lastName, isFocused ->
                if (!isFocused) vm.onFirstLastNameFocusChanged(lastName, false)
            },
            errorListener = vm.lastNameError,
            label = "Last Name",
            modifier = Modifier.padding(top = 8.dp),
        )

        TextFieldWithError(
            onFocusChanged = { email, isFocused ->
                if (!isFocused) vm.onEmailFocusChanged(email)
            },
            errorListener = vm.emailError,
            label = "Email",
        )

        TextFieldWithError(
            onFocusChanged = { phoneNumber, isFocused ->
                if (!isFocused) vm.onPhoneNumberFocusChanged(phoneNumber)
            },
            errorListener = vm.phoneNumberError,
            label = "Phone Number",
        )

        Text("Select Gender")
        CustomDropdownMenu(
            selectedItem = selectedGender,
            options = listOf("Male", "Female", "Other")
        ) { selectedGender = it }

//        Button(
//            onClick = {
//                coroutineScope.launch { vm.onSignUpClicked() }
//            },
//            modifier = Modifier.align(CenterHorizontally),
//        ) {
//            Text(text = "Sign Up")
//        }

        // Display Success/Error Toast
        when (val state = userState.value) {
            is UIState.Error -> {
                getToastHandler(ToastDuration.Short).showToast(state.message)
            }

            UIState.Idle -> {}
            UIState.Loading -> getToastHandler(ToastDuration.Short).showToast("Loading")
            is UIState.Success -> onSignUpSuccess.invoke()
        }
    }
}
