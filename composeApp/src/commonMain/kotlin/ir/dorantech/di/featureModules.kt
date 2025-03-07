package ir.dorantech.di

import di.authModule
import org.kodein.di.DI

val featureModules = DI.Module("featureModules") {
    import(authModule)
}