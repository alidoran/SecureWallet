package ir.dorantech.viewmodel

import ir.dorantech.util.Validation.validateUserName
import ir.dorantech.util.Validation.validatePass
import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.config.AppConfig.SECURITY_LEVEL
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
        val error = validateUserName(
            username = username,
            securityLevel = SECURITY_LEVEL
        )
        _nameError.value = error
    }

    fun onPasswordFocusChanged(password: String) {
        _password.value = password
        val error = validatePass(
            password = password,
            securityLevel = SECURITY_LEVEL
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