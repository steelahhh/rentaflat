package io.github.steelahhh.rent.feature.auth

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import io.github.steelahhh.rent.BR
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityAuthBinding
import io.github.steelahhh.rent.feature.flats.FlatsActivity

class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(), AuthViewModel.EventListener {
    override val layoutId: Int  = R.layout.activity_auth

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<AuthViewModel> = AuthViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory = AppComponent.instance.authSubComponent().viewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        vm.dispatcher.bind(this, this)

        binding.loginActionButton.setOnClickListener {
            val name = binding.userNameTextInput.editText?.text?.toString() ?: ""
            val password = binding.passwordTextInput.editText?.text?.toString() ?: ""
            vm.auth(name, password)
        }
    }

    override fun onAuthFailure(reason: String) {
        MaterialDialog(this)
            .title(R.string.common_error)
            .message(text = reason)
            .cancelable(true)
            .positiveButton(R.string.common_ok)
            .show()

    }

    override fun onAuthSuccess() {
        startActivity(Intent(this, FlatsActivity::class.java))
        finish()
    }
}
