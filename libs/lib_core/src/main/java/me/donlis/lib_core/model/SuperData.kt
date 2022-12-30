package me.donlis.lib_core.model

data class SuperData<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
) {

    fun isSuccess(): Boolean {
        return code == 0
    }
}