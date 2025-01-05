package ir.dorantech

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ir.dorantech.ui.screen.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MvvmKmpLargeScale",
    ) {
        App()
    }
}