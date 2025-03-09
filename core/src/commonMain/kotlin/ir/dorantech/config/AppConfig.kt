package ir.dorantech.config

import ir.dorantech.model.SecurityLevel
import ir.dorantech.model.WebHostSource

object AppConfig {
    val WEB_HOST_SOURCE : WebHostSource =  WebHostSource.Local
    val SECURITY_LEVEL = SecurityLevel.Strong
    val OFFLINE_MODE = false
}