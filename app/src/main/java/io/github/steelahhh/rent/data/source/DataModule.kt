package io.github.steelahhh.rent.data.source

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.github.steelahhh.rent.data.source.local.FlatsDao
import io.github.steelahhh.rent.data.source.local.FlatsDatabase
import io.github.steelahhh.rent.feature.flats.FlatRemoteSource
import io.github.steelahhh.rent.feature.flats.FlatsRepository
import javax.inject.Singleton


/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): FlatsDatabase =
        Room.databaseBuilder(context, FlatsDatabase::class.java, "Flats")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    internal fun provideFlatsDao(database: FlatsDatabase): FlatsDao {
        return database.flatDao
    }

    @Provides
    @Singleton
    fun provideFlatsRemoteSource(context: Context, gson: Gson) = FlatRemoteSource(context, gson)

    @Provides
    @Singleton
    fun provideFlatsRepository(remoteSource: FlatRemoteSource, flatsDao: FlatsDao) =
        FlatsRepository(remoteSource, flatsDao)
}
