package ir.dorantech.remote.util

internal object BaseAddress {
    const val BASE_URL_REMOTE = "https://45.61.134.158:8030/wallet/"
    const val BASE_URL_LOCAL = "http://10.0.2.2:8032/wallet/"
}

internal object AuthEndpoint{
    const val SIGN_UP = "auth/register"
    const val SIGN_IN = "auth/login"
}

internal object UserEndpoint {
    const val GET_USER = "user/get"
}