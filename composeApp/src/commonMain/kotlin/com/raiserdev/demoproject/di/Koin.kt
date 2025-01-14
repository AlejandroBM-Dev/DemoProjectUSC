package com.raiserdev.demoproject.di

import com.raiserdev.demoproject.native.PlatformComponent
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module
import org.koin.mp.KoinPlatform

@Module
@ComponentScan("com.raiserdev.demoproject.data")
class DataModule

@Module(includes = [DataModule::class,ViewModelModule::class, NativeModule::class])
class AppModule

@Module
expect class NativeModule()

fun initKoin(config : KoinAppDeclaration ?= null) {
    startKoin {
        modules(
            AppModule().module,
        )
        config?.invoke(this)
    }
    val hello = KoinPlatform.getKoin().get<PlatformComponent>().sayHello()
    println(hello)

   /* val idGen = KoinPlatform.getKoin().get<IdGenerator> { parametersOf("_prefix_") }.generate()
    println("Id => $idGen")*/
}