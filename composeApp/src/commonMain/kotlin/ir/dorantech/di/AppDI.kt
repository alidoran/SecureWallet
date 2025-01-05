package ir.dorantech.di

import ir.dorantech.di.AppModules.appModules
import ir.dorantech.di.ViewModelModules.viewModelModules
import ir.dorantech.di.module.LocalDataModules.localDataModules
import ir.dorantech.di.module.RemoteDataModules.remoteDataModules
import ir.dorantech.di.module.RepositoryModules.repositoryModules
import ir.dorantech.di.module.UseCaseModules.useCaseModules
import org.kodein.di.DI

val appDI = DI {
    import(remoteDataModules)
    import(localDataModules)
    import(repositoryModules)
    import(useCaseModules)
    import(viewModelModules)
    import(appModules)
}