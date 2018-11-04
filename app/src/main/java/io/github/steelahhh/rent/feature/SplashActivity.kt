package io.github.steelahhh.rent.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.github.steelahhh.rent.BR
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.Preferences
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.core.arch.ViewModelFactory
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivitySplashBinding
import io.github.steelahhh.rent.feature.auth.AuthActivity
import java.util.concurrent.Executor
import javax.inject.Named

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_splash

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<SplashViewModel> = SplashViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        AppComponent.instance.splashSubComponent().viewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.dispatcher.bind(this, this)
        vm.checkAuth()
    }

    override fun routeToAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun routeToFlats() {

    }

}

class SplashViewModel(
    private val preferences: Preferences,
    val dispatcher: EventDispatcher<EventListener>
) : ViewModel() {

    fun checkAuth() {
        val user = preferences.getUser()
        if (user == null) {
            dispatcher.dispatchEvent { routeToAuth() }
        } else {
            dispatcher.dispatchEvent { routeToFlats() }
        }
    }

    interface EventListener {
        fun routeToAuth()
        fun routeToFlats()
    }
}


@Subcomponent(modules = [SplashSubComponent.SplashModule::class])
interface SplashSubComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Module
    class SplashModule {

        @Provides
        fun provideViewModel(
            preferences: Preferences,
            @Named("dispatcher") mainExecutor: Executor
        ): ViewModelProvider.Factory = ViewModelFactory {
            SplashViewModel(preferences, EventDispatcher(mainExecutor))
        }
    }

}
