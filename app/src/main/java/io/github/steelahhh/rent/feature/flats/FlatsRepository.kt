package io.github.steelahhh.rent.feature.flats

import io.github.steelahhh.rent.data.source.remote.FlatRemoteSource
import io.github.steelahhh.rent.data.source.local.FlatsDao
import io.github.steelahhh.rent.model.Flat
import io.github.steelahhh.rent.utils.ioMainSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class FlatsRepository @Inject constructor(
        private val remoteSource: FlatRemoteSource,
        private val localSource: FlatsDao
) {

    fun populateFlats() = Completable.fromCallable {
        localSource.insertAllFlats(remoteSource.getFlats())
    }.ioMainSchedulers()

    fun getFlats() = localSource.getFlats().ioMainSchedulers()

    fun getFlat(id: Int) = localSource.getFlat(id).ioMainSchedulers()

    fun saveFlat(flat: Flat) = Completable.fromCallable { localSource.insertFlat(flat) }.ioMainSchedulers()

    fun updateFlat(flat: Flat) = Completable.fromCallable { localSource.updateFlat(flat) }.ioMainSchedulers()

    val flatCount: Single<Int> get() = localSource.flatCount.ioMainSchedulers()
}