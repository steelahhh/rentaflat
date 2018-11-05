package io.github.steelahhh.rent.feature.flats

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityFlatsBinding
import io.github.steelahhh.rent.feature.auth.AuthActivity
import io.github.steelahhh.rent.feature.flats.FlatDetailActivity.Companion.CREATE_FLAT_REQUEST_CODE
import io.github.steelahhh.rent.model.FlatItem


/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class FlatsActivity : BaseActivity<ActivityFlatsBinding, FlatsViewModel>(), FlatsViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_flats

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<FlatsViewModel> = FlatsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        AppComponent.instance.flatsSubComponent().viewModelFactory()

    private var fastAdapter = FastItemAdapter<FlatItem>().apply {
        hasStableIds()
        withOnClickListener { _, _, item, _ ->
            startActivity(FlatDetailActivity.createIntent(this@FlatsActivity, item.id))
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.dispatcher.bind(this, this)
        setupToolbar()

        binding.flatsRecycler.adapter = fastAdapter
        binding.flatsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val fab = binding.createFlatButton

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fab.isShown) {
                   fab.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        vm.flats.observe(this, Observer {
            fastAdapter.clear()
            fastAdapter.add(it)
        })
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

    override fun routeToCreateFlat() {
        startActivityForResult(EditFlatActivity.createIntent(this, -1, false), CREATE_FLAT_REQUEST_CODE)
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