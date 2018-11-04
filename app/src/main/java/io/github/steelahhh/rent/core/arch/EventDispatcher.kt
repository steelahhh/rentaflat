package io.github.steelahhh.rent.core.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.Executor

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

class EventDispatcher<Listener>(private val executor: Executor) {
    private var eventListener: Listener? = null

    internal val blocks = mutableListOf<Listener.() -> Unit>()

    fun bind(lifecycleOwner: LifecycleOwner, listener: Listener) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun connectListener() {
                eventListener = listener
                executor.execute {
                    blocks.forEach {
                        it(listener)
                    }
                    blocks.clear()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun disconnectListener() {
                eventListener = null
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                source.lifecycle.removeObserver(this)
            }
        })
    }

    fun dispatchEvent(block: Listener.() -> Unit) {
        if (eventListener != null) {
            executor.execute { block(eventListener!!) }
        } else {
            executor.execute { blocks.add(block) }
        }
    }
}
