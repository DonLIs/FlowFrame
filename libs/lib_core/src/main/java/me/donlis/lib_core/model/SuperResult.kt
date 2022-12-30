package me.donlis.lib_core.model

sealed class SuperResult<out T> {

    /**
     * 成功
     * @param data 成功的结果
     */
    data class Success<out T>(val data: T?) : SuperResult<T>()

    /**
     * 失败
     * @param throwable
     */
    data class Failure(val throwable: Throwable) : SuperResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$throwable]"
        }
    }
}

/**
 * 转换为失败结果
 */
fun <T : Throwable> T.toFailureResult(): SuperResult.Failure {
    return SuperResult.Failure(this)
}

/**
 * 是否成功
 */
fun SuperResult<*>.isSuccess(): Boolean {
    return this is SuperResult.Success<*>
}