package ir.dorantech.feature1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ir.dorantech.feature1.viewmodel.UserViewModel
import ir.dorantech.ui.state.UIState
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun Feature1Screen(
    onGetUserByIdClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val viewModel by localDI().instance<UserViewModel>()
        val userState = viewModel.userState.collectAsState()
        val userId = remember { mutableStateOf("0") }
        TextField(userId.value, { userId.value = it })
        Button({onGetUserByIdClick(userId.value)}) {
            Text("Get User By ID")
        }

        when(val state = userState.value) {
            is UIState.Loading -> Text("Loading...")
            is UIState.Success -> {
                val user = state.data
                Column {
                    Text("Welcome, ${user.name}!")
                    Text("Email: ${user.email}")
                }
            }

            is UIState.Error -> Text("Error: ${state.message}")
            UIState.Idle -> {}
        }
    }
}