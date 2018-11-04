package io.github.steelahhh.rent.core.dagger

import dagger.Component
import io.github.steelahhh.rent.core.dagger.modules.ContextModule
import io.github.steelahhh.rent.core.dagger.modules.DispatcherModule
import io.github.steelahhh.rent.feature.auth.dagger.AuthSubComponent
import javax.inject.Singleton

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Singleton
@Component(
    modules = [
        ContextModule::class,
        DispatcherModule::class]
)
interface AppComponent {

    fun authSubComponent(): AuthSubComponent

    companion object {
        lateinit var instance: AppComponent
    }
}