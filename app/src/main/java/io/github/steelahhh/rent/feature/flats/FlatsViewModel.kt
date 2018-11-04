package io.github.steelahhh.rent.feature.flats

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.steelahhh.rent.core.arch.EventDispatcher
import io.github.steelahhh.rent.data.Preferences
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

    fun flats() = repository.getFlats()

    fun logout() {
        preferences.removeUser()
    }

    interface EventListener
}