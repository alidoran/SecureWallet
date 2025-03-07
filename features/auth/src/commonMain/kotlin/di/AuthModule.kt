package di

import ir.dorantech.di.authUiModule
import ir.dorantech.di.authUseCaseModules
import org.kodein.di.DI

val authModule = DI.Module("AuthModule"){
        import(authUiModule)
        import(authUseCaseModules)
}
