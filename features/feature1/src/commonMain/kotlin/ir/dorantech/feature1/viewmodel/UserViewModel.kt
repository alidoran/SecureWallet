package ir.dorantech.feature1.viewmodel

import ir.dorantech.domain.model.UseCaseResult
import ir.dorantech.domain.model.User
import ir.dorantech.domain.usecase.impl.UserByIdUseCase
import ir.dorantech.ui.state.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    val userByIdUseCase: UserByIdUseCase
) {
    private val _userState = MutableStateFlow<UIState<User>>(UIState.Idle)
    val userState: StateFlow<UIState<User>> get() = _userState

    fun fetchUser(
        userId: String,
    ) {
        val isDigitOnly = userId.all { it.isDigit() }
        if (isDigitOnly) {
            _userState.value = UIState.Loading
            CoroutineScope(Dispatchers.IO).launch {
                val result = userByIdUseCase(userId.toInt())
                _userState.value = when (result) {
                    is UseCaseResult.Success -> UIState.Success(result.data)
                    is UseCaseResult.Failure -> UIState.Error(result.errorMessage)
                }
            }
        } else _userState.value = UIState.Error("Invalid Input")
    }
}