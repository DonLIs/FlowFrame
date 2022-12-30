package me.donlis.lib_core.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
interface SuperResponse<out T> : Serializable {

    /**
     * 是否成功
     */
    fun isSuccess(): Boolean

    /**
     * 数据响应体
     */
    fun getBody(): T?

    /**
     * 获取响应码
     */
    fun getCode(): Int?

    /**
     * 获取消息体
     */
    fun getMessage(): String?

}