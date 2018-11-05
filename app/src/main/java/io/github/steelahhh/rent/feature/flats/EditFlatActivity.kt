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
import kotlinx.android.synthetic.main.abc_search_view.*
import java.lang.Exception
import java.util.*

/*
 * Created by Alexander Efimenko on 5/11/18.
 */

class EditFlatActivity : BaseActivity<ActivityFlatEditBinding, FlatsViewModel>(), FlatsViewModel.EventListener {
    override val layoutId: Int = R.layout.activity_flat_edit

    override val vmId: Int = BR.viewModel

    override val viewModelClass: Class<FlatsViewModel> = FlatsViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        AppComponent.instance.flatsSubComponent().viewModelFactory()

    var flatId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(homeAsUp = true)
        vm.dispatcher.bind(this, this)

        flatId = intent.extras?.getInt(FLAT_ID) ?: -1

        if (flatId != -1)
            vm.fetchFlat(flatId)

        binding.floorTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

    override fun routeToCreateFlat() {}

    private fun isValid(): Boolean {
        val address: String?
        val price: Long?
        val area: Double?
        val rooms: Int?
        val floorStr: String
        val floor: Int
        val floors: Int

        try {
             address = binding.addressTextInput.editText?.text?.toString()
             price = binding.priceTextInput.editText?.text?.toString()?.toLong()
             rooms = binding.roomTextInput.editText?.text?.toString()?.toInt()
             area = binding.areaTextInput.editText?.text?.toString()?.toDouble()
             floorStr = binding.floorTextInput.editText?.text?.toString() ?: ""
        } catch (e: Exception) {
            showError(getString(R.string.edit_flat_empty_field))
            return false
        }

        try {
            floor = floorStr.split("/")[0].toInt()
            floors = floorStr.split("/")[1].toInt()
        } catch (e: Exception) {
            showError(getString(R.string.edit_flat_floor_parse_error))
            return false
        }

        if (floor > floors) {
            showError(getString(R.string.edit_flat_floor_error))
            return false
        }

        if (address.isNullOrEmpty()) {
            showError(getString(R.string.edit_flat_address_error))
            return false
        }

        if (price == null || price == 0L) {
            showError(getString(R.string.edit_flat_price_error))
            return false
        }

        if (area == null || area == 0.0) {
            showError(getString(R.string.edit_flat_area_error))
            return false
        }
        if (rooms == null || rooms == 0) {
            showError(getString(R.string.edit_flat_rooms_error))
            return false
        }

        return true
    }

    private fun saveFlat() {
        val flat = vm.flat.value!!

        val isEdit = intent.extras?.getBoolean(INTENT_TYPE) ?: true

        if (!isValid()) {
            return
        }

        val address = binding.addressTextInput.editText?.text?.toString() ?: ""
        val price = binding.priceTextInput.editText?.text?.toString()?.toLong() ?: flat.price
        val rooms = binding.roomTextInput.editText?.text?.toString()?.toInt() ?: flat.rooms
        val area = binding.areaTextInput.editText?.text?.toString()?.toDouble() ?: flat.area
        val floorStr = binding.floorTextInput.editText?.text?.toString() ?: ""

        val floor = floorStr.split("/")[0].toInt()
        val floors = floorStr.split("/")[1].toInt()

        val pricePerSqM = price / area

        val newFlat = flat.copy(
            address = address,
            price = price,
            rooms = rooms,
            pricePerSqM = pricePerSqM,
            floor = floor,
            floors = floors,
            area = area
        )

        if (isEdit) {
            vm.updateFlat(newFlat) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        } else {
            vm.insertFlat(newFlat.copy(id = UUID.randomUUID().toString().hashCode(), image = PLACEHOLDER_IMAGE)) {
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

        const val PLACEHOLDER_IMAGE =
            "https://images.unsplash.com/photo-1483058712412-4245e9b90334?ixlib=rb-0.3.5&s=c1058ecb478c4833b4cbf3133d7d10f1&auto=format&fit=crop&w=500&q=60"

        fun createIntent(context: Context, id: Int, isEdit: Boolean = true) =
            Intent(context, EditFlatActivity::class.java).apply {
                putExtra(FLAT_ID, id)
                putExtra(INTENT_TYPE, isEdit)
            }
    }
}