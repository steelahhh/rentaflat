package io.github.steelahhh.rent.feature.flats

import android.content.Context
import com.google.gson.Gson
import io.github.steelahhh.rent.R
import io.github.steelahhh.rent.model.Flat
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject


/*
 * Created by Alexander Efimenko on 4/11/18.
 */

private data class FlatList(
    val data: List<Flat>
)

class FlatRemoteSource @Inject constructor(val context: Context, val gson: Gson) {

    fun getFlats(): List<Flat> {
        val raw = context.resources.openRawResource(R.raw.flats)
        val rd = BufferedReader(InputStreamReader(raw))

        val data = gson.fromJson<FlatList>(rd, FlatList::class.java).data

        return data
    }
}