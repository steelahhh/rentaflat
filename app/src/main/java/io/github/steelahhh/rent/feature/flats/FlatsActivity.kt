package io.github.steelahhh.rent.feature.flats

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityFlatsBinding
import io.github.steelahhh.rent.feature.auth.AuthActivity

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class FlatsActivity : BaseActivity<ActivityFlatsBinding, FlatsViewModel>(), FlatsViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_flats

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<FlatsViewModel> = FlatsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        AppComponent.instance.flatsSubComponent().viewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.dispatcher.bind(this, this)
        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_flats, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout_action -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        MaterialDialog(this)
            .title(R.string.auth_logout)
            .message(R.string.auth_logout_message)
            .positiveButton(R.string.common_yes) {
                vm.logout()
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
            .negativeButton(R.string.common_no) {
                it.dismiss()
            }
            .show()
    }

}