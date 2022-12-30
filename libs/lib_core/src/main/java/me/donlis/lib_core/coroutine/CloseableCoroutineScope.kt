package me.donlis.lib_core.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class CloseableCoroutineScope(context: CoroutineContext) : Closeable,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = context

    override fun close() {
        try {
            coroutineContext.cancel()
        } catch (e: Exception) {
        }
    }
}