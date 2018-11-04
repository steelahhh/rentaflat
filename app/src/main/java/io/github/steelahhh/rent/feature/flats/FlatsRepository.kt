package io.github.steelahhh.rent.feature.flats

import io.github.steelahhh.rent.data.source.local.FlatsDao
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

    fun getFlat(id: Int) = localSource.getFlat(id)

    val flatCount: Single<Int> get() = localSource.flatCount.ioMainSchedulers()
}