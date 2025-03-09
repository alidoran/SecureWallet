package ir.dorantech.di

import ir.dorantech.di.RepositoryModules.repositoryModules
import ir.dorantech.domain.di.useCaseModules
import ir.dorantech.local.di.LocalModules.localDataModules
import ir.dorantech.remote.di.RemoteModules.remoteDataModules
import org.kodein.di.DI

val CoreModules = DI.Module("appModule") {
    import(localDataModules)
    import(remoteDataModules)
    import(repositoryModules)
    import(useCaseModules)
}