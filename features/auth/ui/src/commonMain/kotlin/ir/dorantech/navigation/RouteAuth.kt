package ir.dorantech.navigation

import kotlinx.serialization.Serializable

sealed interface RouteAuth {
    @Serializable
    data object SignInRoute: RouteAuth

    @Serializable
    data object SignUpRoute: RouteAuth

    @Serializable
    data object ForgotPasswordRoute: RouteAuth
}