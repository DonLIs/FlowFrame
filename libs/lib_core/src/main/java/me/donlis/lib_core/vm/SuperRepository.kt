package me.donlis.lib_core.vm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import me.donlis.lib_core.model.SuperResponse
import kotlin.coroutines.CoroutineContext

open class SuperRepository {

    // 默认适配器
    private val defaultResponseAdapter: ResponseAdapter by lazy {
        DefaultResponseAdapter()
    }

    // 默认异常转换器
    private val defaultExceptionConverter: ExceptionConverter by lazy {
        DefaultExceptionConverter()
    }

    /**
     * 获取适配器
     */
    protected open fun getResponseAdapter(): ResponseAdapter {
        return defaultResponseAdapter
    }

    /**
     * 获取异常转换器
     */
    protected open fun getExceptionConverter(): ExceptionConverter {
        return defaultExceptionConverter
    }

    /**
     * Flow - 接口
     * @param adapter 响应适配器
     * @param converter 异常转换器
     * @param context 协程上下文
     * @param block 发起请求
     */
    fun <T> flowApi(
        adapter: ResponseAdapter = getResponseAdapter(),
        converter: ExceptionConverter = getExceptionConverter(),
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> SuperResponse<T>
    ): Flow<T?> {
        return flow {
            // 拿取响应体
            val response = block.invoke()
            // 响应出去，需要捕获异常
            emit(adapter.getResponseBody(response))
        }.catch {
            throw converter.convert(it)
        }.flowOn(context)
    }

    fun <T> ofFlow(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> T
    ): Flow<T?> {
        return flow {
            val response = block.invoke()
            emit(response)
        }.catch {
            throw it
        }.flowOn(context)
    }

}