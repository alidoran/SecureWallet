package ir.dorantech.util

object AppConfig {
    val webHostSource : WebHostSource =  WebHostSource.Local
}

sealed class WebHostSource{
    data object Local: WebHostSource()
    data object Remote: WebHostSource()
}