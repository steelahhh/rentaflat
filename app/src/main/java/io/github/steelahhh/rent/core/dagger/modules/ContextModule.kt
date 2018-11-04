package io.github.steelahhh.rent.core.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Module
class ContextModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context = context
}