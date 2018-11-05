package io.github.steelahhh.rent.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.steelahhh.rent.model.Flat

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Database(entities = [Flat::class], version = 4, exportSchema = false)
abstract class FlatsDatabase : RoomDatabase() {
    abstract val flatDao: FlatsDao
}
