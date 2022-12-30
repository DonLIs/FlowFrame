package me.donlis.lib_core.vm

import me.donlis.lib_core.model.SuperResponse
import me.donlis.lib_core.model.SuperResult


interface ResponseAdapter {

    /**
     * 获取接口响应体
     */
    fun <T> getResponseBody(response: SuperResponse<T>): T?

    /**
     * 接口响应转换为响应结果
     * (根据状态码等区分出成功与失败)
     */
    fun <T> responseToResult(response: SuperResponse<T>): SuperResult<T>

    /**
     * 请求过程的异常转换为响应结果
     */
    fun throwableToResult(throwable: Throwable): SuperResult<Nothing>
}