package ir.dorantech.navigation

import kotlinx.serialization.Serializable

sealed interface RouteApp {
    @Serializable
    data object MainRoute : RouteApp

    @Serializable
    data object Feature1Route : RouteApp
}