package io.github.steelahhh.rent.core.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.steelahhh.rent.core.Preferences
import javax.inject.Singleton

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Module
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferences(context: Context): Preferences =
            Preferences(context.getSharedPreferences("prefs", Context.MODE_PRIVATE))
}