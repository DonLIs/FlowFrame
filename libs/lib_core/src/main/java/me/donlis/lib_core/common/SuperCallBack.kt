package me.donlis.lib_core.common

interface SuperCallBack<T> : IDispatchers {

    /**
     * 成功
     * @param result 结果
     */
    fun onSuccess(result: T)

    /**
     * 失败
     * @param throwable 错误信息
     */
    fun onFailure(throwable: Throwable)

}