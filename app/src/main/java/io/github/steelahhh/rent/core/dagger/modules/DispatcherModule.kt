package io.github.steelahhh.rent.core.dagger.modules

import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Named

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Module
class DispatcherModule {
    @Provides
    @Named("dispatcher")
    fun provideMainThreadExecutor(): Executor {
        val handler = Handler(Looper.getMainLooper())
        return Executor { handler.post(it) }
    }
}