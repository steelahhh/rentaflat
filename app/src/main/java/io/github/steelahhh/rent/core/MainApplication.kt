package io.github.steelahhh.rent.core

import android.app.Application
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.core.dagger.DaggerAppComponent
import io.github.steelahhh.rent.core.dagger.modules.ContextModule

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AppComponent.instance = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()
    }
}