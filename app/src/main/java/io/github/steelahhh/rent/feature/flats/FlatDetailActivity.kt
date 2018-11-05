package io.github.steelahhh.rent.feature.flats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityFlatsBinding

/*
 * Created by Alexander Efimenko on 5/11/18.
 */

class FlatDetailActivity : BaseActivity<ActivityFlatsBinding, FlatsViewModel>(), FlatsViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_flat_detail

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<FlatsViewModel> = FlatsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
            AppComponent.instance.flatsSubComponent().viewModelFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(homeAsUp = true)
        vm.dispatcher.bind(this, this)

        intent.extras?.getInt(FLAT_ID)?.let {
            vm.fetchFlat(it)
        }
    }

    companion object {
        const val FLAT_ID = "args: Flat ID"

        fun createIntent(context: Context, id: Int) = Intent(context, FlatDetailActivity::class.java).apply {
            putExtra(FLAT_ID, id)
        }
    }
}