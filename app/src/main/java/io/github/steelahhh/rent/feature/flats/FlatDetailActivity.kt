package io.github.steelahhh.rent.feature.flats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
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

    var flatId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(homeAsUp = true)
        vm.dispatcher.bind(this, this)

        flatId = intent.extras?.getInt(FLAT_ID) ?: 0

        vm.fetchFlat(flatId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.edit_action -> {
                startActivityForResult(
                    EditFlatActivity.createIntent(this@FlatDetailActivity, flatId),
                    EDIT_FLAT_REQUEST_CODE
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == EDIT_FLAT_REQUEST_CODE) {
            vm.fetchFlat(flatId)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun routeToCreateFlat() {}

    companion object {
        const val EDIT_FLAT_REQUEST_CODE = 1001
        const val CREATE_FLAT_REQUEST_CODE = 1002
        const val FLAT_ID = "args: Flat ID"

        fun createIntent(context: Context, id: Int) = Intent(context, FlatDetailActivity::class.java).apply {
            putExtra(FLAT_ID, id)
        }
    }
}