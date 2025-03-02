package ir.dorantech.remote.model

internal sealed interface Endpoint {
    data object SignIn : Endpoint
    data object SignUp : Endpoint
    data object ForgotPassword : Endpoint
    data object ResetPassword : Endpoint
    data object User : Endpoint
}