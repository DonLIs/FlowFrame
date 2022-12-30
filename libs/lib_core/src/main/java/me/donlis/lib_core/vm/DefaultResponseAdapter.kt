package me.donlis.lib_core.vm

import me.donlis.lib_core.exceptions.ApiException
import me.donlis.lib_core.model.SuperResponse
import me.donlis.lib_core.model.SuperResult

open class DefaultResponseAdapter : ResponseAdapter {
    /**
     * 获取接口响应体
     */
    override fun <T> getResponseBody(response: SuperResponse<T>): T? {
        if (response.isSuccess()) {
            return response.getBody()?.let {
                return@let it
            } ?: let {
                // 这里根据接口规则，可灵活调整
                return@let null
                //throw NullPointerException("response body is Null")
            }
        } else {
            // 接口异常
            throw ApiException(response)
        }
    }

    /**
     * 接口响应转换为响应结果保证体
     */
    override fun <T> responseToResult(response: SuperResponse<T>): SuperResult<T> {
        return try {
            val body = getResponseBody(response)
            SuperResult.Success(data = body)
        } catch (e: Exception) {
            throwableToResult(e)
        }
    }

    /**
     * 接口请求异常转换为响应结果
     */
    override fun throwableToResult(throwable: Throwable): SuperResult<Nothing> {
        return SuperResult.Failure(throwable)
    }
}