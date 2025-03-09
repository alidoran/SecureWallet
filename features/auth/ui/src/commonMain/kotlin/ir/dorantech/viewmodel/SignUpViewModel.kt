package ir.dorantech.viewmodel

import ir.dorantech.util.Validation.validateEmail
import ir.dorantech.util.Validation.validateUserName
import ir.dorantech.util.Validation.validatePass
import ir.dorantech.util.Validation.validatePhoneNumber
import ir.dorantech.config.AppConfig.SECURITY_LEVEL
import ir.dorantech.ui.state.UIState
import ir.dorantech.usecase.SignUpUseCase
import ir.dorantech.util.Validation.validateFirstLastName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) {
    private val _userName = MutableStateFlow("")
    private val _userNameError = MutableStateFlow("")
    val userNameError = _userNameError.asStateFlow()
    private val _password = MutableStateFlow("")
    private val _passwordError = MutableStateFlow("")
    val passwordError = _passwordError.asStateFlow()
    private val _passwordConfirmError = MutableStateFlow("")
    val passwordConfirmError = _passwordConfirmError.asStateFlow()
    private val _signUpState = MutableStateFlow<UIState<Unit>>(UIState.Idle)
    private val _firstName = MutableStateFlow("")
    private val _firstNameError = MutableStateFlow("")
    val firstNameError = _firstNameError.asStateFlow()
    private val _lastName = MutableStateFlow("")
    private val _lastNameError = MutableStateFlow("")
    val lastNameError = _lastNameError.asStateFlow()
    private val _email = MutableStateFlow("")
    private val _emailError = MutableStateFlow("")
    val emailError = _emailError.asStateFlow()
    private val _phoneNumber = MutableStateFlow("")
    private val _phoneNumberError = MutableStateFlow("")
    val phoneNumberError = _phoneNumberError.asStateFlow()
    val signUpState = _signUpState.asStateFlow()

    fun onUserNameFocusChanged(username: String) {
        _userName.value = username
        val error = validateUserName(
            username = username,
            securityLevel = SECURITY_LEVEL
        )
        _userNameError.value = error
    }

    fun onPasswordFocusChanged(password: String) {
        _password.value = password
        val error = validatePass(
            password = password,
            securityLevel = SECURITY_LEVEL
        )
        _passwordError.value = error
    }

    fun onConfirmPasswordFocusChanged(password: String) {
        if (password != _password.value) {
            _passwordError.value = "Passwords do not match"
        }
        val error = validatePass(
            password = password,
            securityLevel = SECURITY_LEVEL
        )
        _passwordError.value = error
    }

    fun onFirstLastNameFocusChanged(firstName: String, isFirstName: Boolean) {
        _firstName.value = firstName
        val error = validateFirstLastName(
            firstName = firstName,
            securityLevel = SECURITY_LEVEL
        )
        if (isFirstName) _firstNameError.value = error
        else _lastNameError.value = error
    }

    fun onEmailFocusChanged(email: String) {
        _email.value = email
        val error = validateEmail(
            email = email,
            securityLevel = SECURITY_LEVEL
        )
        _emailError.value = error
    }

    fun onPhoneNumberFocusChanged(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
        val error = validatePhoneNumber(
            phoneNumber = phoneNumber,
            securityLevel = SECURITY_LEVEL
        )
        _phoneNumberError.value = error
    }

//    fun onSendClicked() {
//        val signUpRequest = SignUpRequest(
//
//        )
//    }
}