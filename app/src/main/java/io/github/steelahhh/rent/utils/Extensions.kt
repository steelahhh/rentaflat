package io.github.steelahhh.rent.utils

import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

fun Toolbar.changeToolbarFont() {
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (view is TextView && view.text == title) {
            view.typeface = Typeface.createFromAsset(view.context.assets, "manrope_bold.otf")
            break
        }
    }
}

fun Completable.ioMainSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun Completable.ioSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Observable<T>.ioMainSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.ioSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Single<T>.ioMainSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.ioSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Maybe<T>.ioMainSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.ioSchedulers() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> MutableLiveData<T>.default(value: T): MutableLiveData<T> {
    this.value = value
    return this
}