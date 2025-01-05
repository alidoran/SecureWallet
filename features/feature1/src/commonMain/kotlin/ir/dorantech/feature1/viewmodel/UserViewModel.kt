package ir.dorantech.feature1.viewmodel

import ir.dorantech.domain.model.UserModel
import ir.dorantech.domain.usecase.UserByIdUseCase
import ir.dorantech.domain.result.DomainError
import ir.dorantech.domain.result.DomainResult
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
    private val _userState = MutableStateFlow<UIState<UserModel>>(UIState.Idle)
    val userState: StateFlow<UIState<UserModel>> get() = _userState

    fun fetchUser(
        userId: String,
    ) {
        val isDigitOnly = userId.all { it.isDigit() }
        if (isDigitOnly) {
            _userState.value = UIState.Loading
            CoroutineScope(Dispatchers.IO).launch {
                val result = userByIdUseCase(userId)
                _userState.value = when (result) {
                    is DomainResult.Failure -> UIState.Error(errorHandler(result))
                    is DomainResult.Success -> UIState.Success(result.data)
                }
            }
        } else _userState.value = UIState.Error("Invalid Input")
    }

    private fun errorHandler(dataError: DomainResult.Failure) =
        when (dataError.error) {
            is DomainError.InvalidInput -> "Invalid Input"
            is DomainError.Unauthorized -> "Unauthorized"
            is DomainError.TryLater -> "Try Later"
            is DomainError.Unknown -> "Unknown Error"
        }
}