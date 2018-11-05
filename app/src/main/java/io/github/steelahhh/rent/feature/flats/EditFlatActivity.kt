package io.github.steelahhh.rent.feature.flats

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.base.BaseActivity
import io.github.steelahhh.rent.core.dagger.AppComponent
import io.github.steelahhh.rent.databinding.ActivityFlatEditBinding
import java.lang.Exception

/*
 * Created by Alexander Efimenko on 5/11/18.
 */

class EditFlatActivity : BaseActivity<ActivityFlatEditBinding, FlatsViewModel>(), FlatsViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_flat_edit

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

        binding.floorTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("TAGGO", "$s $start $before $count")
                val str = s?.toString() ?: ""
                if (str.length == 1 && count == 1) {
                    binding.floorTextInput.editText?.setText(getString(R.string.edit_flat_floor_holder, str, ""))
                    binding.floorTextInput.editText?.setSelection(str.length + 1)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_check, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.check_action -> {
                saveFlat()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveFlat() {
        val flat = vm.flat.value!!

        val isEdit = intent.extras?.getBoolean(INTENT_TYPE) ?: true

        val address = binding.addressTextInput.editText?.text?.toString() ?: ""
        val price = binding.priceTextInput.editText?.text?.toString()?.toLong() ?: flat.price
        val rooms = binding.roomTextInput.editText?.text?.toString()?.toInt() ?: flat.rooms
        val area = binding.areaTextInput.editText?.text?.toString()?.toDouble() ?: flat.area
        val floorStr = binding.floorTextInput.editText?.text?.toString() ?: ""

        var floor = flat.floor
        var floors = flat.floors

        try {
            floor = floorStr.split("/")[0].toInt()
            floors = floorStr.split("/")[1].toInt()
        } catch (e: Exception) {
            showError(getString(R.string.edit_flat_floor_parse_error))
            return
        }

        if (floor > floors) {
            showError(getString(R.string.edit_flat_floor_error))
            return
        }

        val pricePerSqM = price / area

        val newFlat = flat.copy(
            address = address,
            price = price,
            rooms = rooms,
            pricePerSqM = pricePerSqM,
            floor = floor,
            floors = floors
        )

        if (isEdit) {
            vm.updateFlat(newFlat) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        } else {
            vm.insertFlat(flat) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun showError(message: String) {
        MaterialDialog(this)
            .title(R.string.common_error)
            .message(text = message)
            .positiveButton(R.string.common_ok)
            .show()
    }

    companion object {
        const val FLAT_ID = "args: Flat ID"
        const val INTENT_TYPE = "args: Intent Type"

        fun createIntent(context: Context, id: Int, isEdit: Boolean = true) =
            Intent(context, EditFlatActivity::class.java).apply {
                putExtra(FLAT_ID, id)
                putExtra(INTENT_TYPE, isEdit)
            }
    }
}