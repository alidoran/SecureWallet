package ir.dorantech.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        onSignInSuccess = {}
    )
}