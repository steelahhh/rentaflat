package io.github.steelahhh.rent.feature.auth

import androidx.lifecycle.ViewModel
import io.github.steelahhh.rent.core.arch.EventDispatcher

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class AuthViewModel(val dispatcher: EventDispatcher<EventListener>): ViewModel() {

    interface EventListener
}

