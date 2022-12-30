package me.donlis.lib_core.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineUtils {

    /**
     * io协程，运行在io线程
     */
    fun io(
        coroutineScope: CoroutineScope? = null,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ): Job {
        return coroutineScope?.launch(Dispatchers.IO, start) {
            block()
        } ?: GlobalScope.launch(Dispatchers.IO, start) {
            block()
        }
    }

    /**
     * 可以绑定生命周期的io协程
     */
    fun io(
        lifecycleOwner: LifecycleOwner,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ): Job {
        val supervisorJob = SupervisorJob()
        CoroutineScope(Dispatchers.IO + supervisorJob).launch(start = start) {
            block()
        }
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                supervisorJob.cancel()
                lifecycleOwner.lifecycle.removeObserver(this)
            }
        })
        return supervisorJob
    }

    /**
     * 运行在主线程的协程
     */
    fun main(
        coroutineScope: CoroutineScope? = null,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ): Job {
        return coroutineScope?.launch(Dispatchers.Main, start) {
            block()
        } ?: GlobalScope.launch(Dispatchers.Main, start) {
            block()
        }
    }

    /**
     * 可以绑定生命周期的main协程
     */
    fun main(
        lifecycleOwner: LifecycleOwner,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ): Job {
        val supervisorJob = SupervisorJob()
        CoroutineScope(Dispatchers.Main + supervisorJob).launch(start = start) {
            block()
        }
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                supervisorJob.cancel()
                lifecycleOwner.lifecycle.removeObserver(this)
            }
        })
        return supervisorJob
    }

    /**
     * 有返回值的协程
     */
    fun <T> async(
        coroutineScope: CoroutineScope? = null,
        context: CoroutineContext? = null,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return coroutineScope?.async(context ?: EmptyCoroutineContext, start) {
            block()
        } ?: GlobalScope.async(context ?: EmptyCoroutineContext, start) {
            block()
        }
    }

    /**
     * 执行一个协程
     */
    fun launch(
        coroutineScope: CoroutineScope? = null,
        context: CoroutineContext? = null,
        start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
    ): Job {
        return coroutineScope?.launch(context ?: EmptyCoroutineContext, start) {
            block()
        } ?: GlobalScope.async(context ?: EmptyCoroutineContext, start) {
            block()
        }
    }
}