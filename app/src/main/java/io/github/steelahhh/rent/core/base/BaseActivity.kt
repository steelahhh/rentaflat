package io.github.steelahhh.rent.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.utils.changeToolbarFont

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var binding: DB
    protected abstract val layoutId: Int

    protected abstract val vmId: Int
    protected lateinit var vm: VM
    protected abstract val viewModelClass: Class<VM>
    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, viewModelFactory())[viewModelClass]

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setLifecycleOwner(this)
        binding.setVariable(vmId, vm)
    }

    fun setupToolbar(homeAsUp: Boolean = false) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        if (toolbar != null) {
            setSupportActionBar(toolbar)

            toolbar.changeToolbarFont()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUp)
    }
}