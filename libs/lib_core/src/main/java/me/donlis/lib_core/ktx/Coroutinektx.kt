package me.donlis.lib_core.ktx

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.donlis.lib_core.coroutine.CoroutineUtils
import me.donlis.lib_core.model.SuperFlowWrapper
import kotlin.coroutines.CoroutineContext


suspend fun Any.mainInCoroutine(block: suspend CoroutineScope.() -> Unit
) {
    withContext(Dispatchers.Main) {
        block.invoke(this)
    }
}

fun Any?.main(
    start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineUtils.main((this as? CoroutineScope), start, block)
}

fun Any?.main(
    lifecycleOwner: LifecycleOwner,
    start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineUtils.main(lifecycleOwner, start, block)
}

fun <T> Any?.async(
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return CoroutineUtils.async((this as? CoroutineScope), context, start, block)
}

fun Any?.io(
    start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineUtils.io((this as? CoroutineScope), start, block)
}

fun Any?.io(
    lifecycleOwner: LifecycleOwner,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineUtils.io(lifecycleOwner, start, block)
}

fun CoroutineContext?.launch(coroutineScope: CoroutineScope? = null, block: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineUtils.launch(coroutineScope = coroutineScope, context = this, block = block)
}

fun <T> CoroutineScope.ofSharedFlow(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend () -> T
): Flow<SuperFlowWrapper<T>> {
    return flow {
        val response = block.invoke()
        emit(SuperFlowWrapper(response))
    }.flowOn(context).catch {
        emit(SuperFlowWrapper(throwable = it))
    }.shareIn(this, SharingStarted.Lazily)
}

fun <T> CoroutineScope.ofStateFlow(
    default: T,
    context: CoroutineContext = Dispatchers.IO,
    block: suspend () -> T
): StateFlow<SuperFlowWrapper<T>> {
    return flow {
        val response = block.invoke()
        emit(SuperFlowWrapper(response))
    }.flowOn(context).catch {
        emit(SuperFlowWrapper(throwable = it))
    }.stateIn(this, SharingStarted.Lazily, SuperFlowWrapper(data = default))
}