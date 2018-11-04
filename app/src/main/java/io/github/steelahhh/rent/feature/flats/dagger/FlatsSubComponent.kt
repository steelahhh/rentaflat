package io.github.steelahhh.rent.feature.flats.dagger

import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.core.arch.ViewModelFactory
import io.github.steelahhh.rent.data.Preferences
import io.github.steelahhh.rent.data.source.local.FlatsDao
import io.github.steelahhh.rent.feature.flats.FlatRemoteSource
import io.github.steelahhh.rent.feature.flats.FlatsRepository
import io.github.steelahhh.rent.feature.flats.FlatsViewModel
import java.util.concurrent.Executor
import javax.inject.Named
import javax.inject.Singleton

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
            preferences: Preferences,
            repository: FlatsRepository,
            @Named("dispatcher") mainExecutor: Executor
        ): ViewModelProvider.Factory = ViewModelFactory {
            FlatsViewModel(preferences, repository, EventDispatcher(mainExecutor))
        }
    }

}