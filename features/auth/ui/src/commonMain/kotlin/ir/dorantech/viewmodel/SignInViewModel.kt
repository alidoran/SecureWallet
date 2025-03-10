package ir.dorantech.viewmodel

import ir.dorantech.Validation.nameValidation
import ir.dorantech.Validation.passwordValidation
import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.model.SecurityLevel
import ir.dorantech.model.SignInRequest
import ir.dorantech.ui.state.UIState
import ir.dorantech.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(
    val signInUseCase: SignInUseCase
) {
    private val _name = MutableStateFlow("")
    private val _nameError = MutableStateFlow("")
    val nameError = _nameError.asStateFlow()
    private val _password = MutableStateFlow("")
    private val _passwordError = MutableStateFlow("")
    val passwordError = _passwordError.asStateFlow()
    private val _signInState = MutableStateFlow<UIState<Unit>>(UIState.Idle)
    val signInState = _signInState.asStateFlow()


    fun onNameFocusChanged(username: String) {
        _name.value = username
        val error = nameValidation(
            username = username,
            securityLevel = SecurityLevel.Strong
        )
        _nameError.value = error
    }

    fun onPasswordFocusChanged(password: String) {
        _password.value = password
        val error = passwordValidation(
            password = password,
            securityLevel = SecurityLevel.Strong
        )
        _passwordError.value = error
    }

    suspend fun onSendClicked() {
        val signInRequest = SignInRequest(_name.value, _password.value)
        _signInState.value = UIState.Loading
        _signInState.value = when (val result = signInUseCase(signInRequest)) {
            is UseCaseResult.Success -> UIState.Success(Unit)
            is UseCaseResult.Failure -> UIState.Error(result.errorMessage)
        }
    }
}