package io.github.steelahhh.rent.feature.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import io.github.steelahhh.rent.BuildConfig
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.core.Preferences
import io.github.steelahhh.rent.core.arch.EventDispatcher

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class AuthViewModel(
    val context: Context,
    val prefs: Preferences,
    val dispatcher: EventDispatcher<EventListener>
) : ViewModel() {

    fun auth(login: String, password: String) {
        if (login == BuildConfig.USERNAME && password == BuildConfig.PASSWORD) {
            prefs.saveUser(login)
            dispatcher.dispatchEvent { onAuthSuccess() }
        } else {
            dispatcher.dispatchEvent { onAuthFailure(context.getString(R.string.auth_error)) }
        }
    }

    interface EventListener {
        fun onAuthSuccess()
        fun onAuthFailure(reason: String)
    }
}

