package io.github.steelahhh.rent.feature.flats.dagger

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.github.steelahhh.rent.core.Preferences
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.core.arch.ViewModelFactory
import io.github.steelahhh.rent.feature.flats.FlatsViewModel
import java.util.concurrent.Executor
import javax.inject.Named

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Subcomponent(modules = [FlatsSubComponent.FlatsModule::class])
interface FlatsSubComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Module
    class FlatsModule {

        @Provides
        fun provideViewModel(
            context: Context,
            preferences: Preferences,
            @Named("dispatcher") mainExecutor: Executor
        ): ViewModelProvider.Factory = ViewModelFactory {
            FlatsViewModel(preferences, EventDispatcher(mainExecutor))
        }
    }

}