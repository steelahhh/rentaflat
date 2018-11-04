package io.github.steelahhh.rent.feature.auth

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import io.github.steelahhh.rent.BR
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(), AuthViewModel.EventListener {
    override val layoutId: Int  = R.layout.activity_auth

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<AuthViewModel> = AuthViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory = AppComponent.instance.authSubComponent().viewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        vm.dispatcher.bind(this, this)
    }
}
