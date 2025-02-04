package ir.dorantech.remote.util

import ir.dorantech.remote.model.Endpoint
import ir.dorantech.remote.util.AuthEndpoint.SIGN_IN
import ir.dorantech.remote.util.AuthEndpoint.SIGN_UP
import ir.dorantech.remote.util.UserEndpoint.GET_USER

internal class GetEndPoint(
    private val baseUrl: String
) {
    operator fun invoke(
        endpoint: Endpoint
    ): String {
        return baseUrl + when (endpoint) {
            Endpoint.SignIn -> SIGN_IN
            Endpoint.ForgotPassword -> TODO()
            Endpoint.ResetPassword -> TODO()
            Endpoint.SignUp -> SIGN_UP
            Endpoint.User -> GET_USER
        }
    }
}