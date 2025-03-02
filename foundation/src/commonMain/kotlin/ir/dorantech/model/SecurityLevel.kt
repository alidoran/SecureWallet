package ir.dorantech.model

sealed class SecurityLevel {
    data object Weak : SecurityLevel()
    data object Medium : SecurityLevel()
    data object Strong : SecurityLevel()
}