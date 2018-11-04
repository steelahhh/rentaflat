package io.github.steelahhh.rent.core.dagger.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Module
class ApplicationModule {
    @Provides
    fun provideGson(): Gson = Gson()
}