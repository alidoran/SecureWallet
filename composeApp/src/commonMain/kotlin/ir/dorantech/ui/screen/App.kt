package ir.dorantech.ui.screen

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import ir.dorantech.di.appDI
import ir.dorantech.navigation.NavigationScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.kodein.di.compose.withDI

@Composable
@Preview
fun App() = withDI(di = appDI) {
    MaterialTheme {
        NavigationScreen()
    }
}