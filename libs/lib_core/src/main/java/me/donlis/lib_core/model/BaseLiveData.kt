package me.donlis.lib_core.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.donlis.lib_core.common.SuperCallBack

open class BaseLiveData<T> : MutableLiveData<SuperResult<T>>(), SuperCallBack<T> {

    override fun onSuccess(result: T) {
        postValue(SuperResult.Success(result))
    }

    override fun onFailure(throwable: Throwable) {
        postValue(SuperResult.Failure(throwable))
    }

    /**
     * 获取成功数据
     */
    fun getSuccessValue(): T? {
        return (value as? SuperResult.Success<T>)?.data
    }

    /**
     * 获取错误数据
     */
    fun getFailureValue(): Throwable? {
        return (value as? SuperResult.Failure)?.throwable
    }

    /**
     * 添加观察者
     * @param owner 生命周期
     * @param success 成功回调
     * @param failure 失败回调
     */
    open fun observe(
        owner: LifecycleOwner,
        success: (value: T?) -> Unit = {},
        failure: (e: Throwable) -> Unit = {}
    ): BaseLiveData<T> {
        super.observe(owner, Observer<SuperResult<T>> {
            when (it) {
                is SuperResult.Failure -> {
                    failure(it.throwable)
                }
                is SuperResult.Success -> {
                    success(it.data)
                }
            }
        })
        return this
    }
}