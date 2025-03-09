package ir.dorantech.model

sealed class WebHostSource{
    data object Local: WebHostSource()
    data object Remote: WebHostSource()
}