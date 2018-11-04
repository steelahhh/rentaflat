package io.github.steelahhh.rent.feature.flats

import androidx.lifecycle.ViewModel
import io.github.steelahhh.rent.core.Preferences
import io.github.steelahhh.rent.core.arch.EventDispatcher

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class FlatsViewModel(
    private val preferences: Preferences,
    val dispatcher: EventDispatcher<EventListener>
) : ViewModel() {

    fun logout() {
        preferences.removeUser()
    }

    interface EventListener
}