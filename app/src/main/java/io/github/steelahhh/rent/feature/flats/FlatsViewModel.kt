package io.github.steelahhh.rent.feature.flats

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.data.Preferences
import io.github.steelahhh.rent.model.Flat
import io.github.steelahhh.rent.model.FlatItem
import io.github.steelahhh.rent.utils.toLiveData

/*
 * Created by Alexander Efimenko on 4/11/18.
 */
@SuppressLint("CheckResult")
class FlatsViewModel(
        private val preferences: Preferences,
        private val repository: FlatsRepository,
        val dispatcher: EventDispatcher<EventListener>
) : ViewModel() {

    val flats: LiveData<List<FlatItem>> = Transformations.map(repository.getFlats().toLiveData()) { flats ->
        flats.map {
            FlatItem(it.id, it.image, it.address, it.area, it.price)
        }
    }

    val flat: MutableLiveData<Flat> = MutableLiveData()

    fun fetchFlat(id: Int) {
        repository.getFlat(id).subscribe({ flat.postValue(it) }, { it.printStackTrace() })
    }

    fun insertFlat(flat: Flat, action: () -> Unit) {
        repository.saveFlat(flat).subscribe({ action() }, { it.printStackTrace() })
    }

    fun updateFlat(flat: Flat, action: () -> Unit) {
        repository.updateFlat(flat).subscribe({ action() }, { it.printStackTrace() })
    }

    fun logout() {
        preferences.removeUser()
    }

    interface EventListener
}