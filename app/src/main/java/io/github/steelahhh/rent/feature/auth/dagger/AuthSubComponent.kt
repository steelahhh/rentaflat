package io.github.steelahhh.rent.feature.auth.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.core.arch.ViewModelFactory
import io.github.steelahhh.rent.feature.auth.AuthViewModel
import java.util.concurrent.Executor
import javax.inject.Named

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Subcomponent(modules = [AuthSubComponent.AuthModule::class])
interface AuthSubComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Module
    class AuthModule {

        @Provides
        fun provideViewModel(@Named("dispatcher") mainExecutor: Executor):
                ViewModelProvider.Factory = ViewModelFactory {
            AuthViewModel(EventDispatcher(mainExecutor))
        }
    }

}