package me.donlis.lib_core.common

import kotlin.coroutines.CoroutineContext

interface IDispatchers {

    /**
     * 获取协程上下文
     * @return null
     */
    fun getCoroutineContext(): CoroutineContext? {
        return null
    }

}