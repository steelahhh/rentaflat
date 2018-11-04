package io.github.steelahhh.rent.core

import android.content.SharedPreferences
import io.github.steelahhh.rent.model.User
import java.util.*

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class Preferences(private val prefs: SharedPreferences) {

    fun getUser(): User? {
        val id = prefs.getInt(USER_ID, -1)
        val name = prefs.getString(USER_NAME, null)

        if (id != -1 && name != null) {
            return User(id, name)
        }

        return null
    }

    fun saveUser(name: String) {
        prefs.edit()
            .putInt(USER_ID, UUID.randomUUID().toString().hashCode())
            .putString(USER_NAME, name)
            .apply()
    }

    fun removeUser() {
        prefs.edit()
            .remove(USER_NAME)
            .remove(USER_ID)
            .apply()
    }

    companion object {
        const val USER_ID = "prefs:User Id"
        const val USER_NAME = "prefs:User Name"
    }
}