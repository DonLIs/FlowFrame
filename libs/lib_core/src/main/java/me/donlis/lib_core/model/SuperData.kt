package me.donlis.lib_core.model

open class SuperData<T> : SuperResponse<T> {

    private val code: Int? = null

    private val data: T? = null

    private val message: String? = null

    override fun isSuccess(): Boolean {
        return code == 0
    }

    override fun getBody(): T? {
        return data
    }

    override fun getCode(): Int? {
        return code
    }

    override fun getMessage(): String? {
        return message
    }
}