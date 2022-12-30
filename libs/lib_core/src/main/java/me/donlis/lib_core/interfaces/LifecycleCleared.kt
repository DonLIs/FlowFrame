package me.donlis.lib_core.interfaces

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner


/**
 * 跟随目标生命周期销毁
 **/
interface LifecycleCleared : LifecycleEventObserver {

    /**
     * 是否启用
     */
    fun isEnabledLifecycleClear(): Boolean {
        return true
    }

    /**
     * 获取监听的目标生命周期
     */
    abstract fun getTargetLifecycle(): Lifecycle?

    /**
     * 目标生命周期已销毁：执行清除资源操作
     */
    abstract fun onTargetCleared()

    /**
     * 获取要执行清理的事件
     */
    fun getClearEvent(): Lifecycle.Event? {
        return Lifecycle.Event.ON_DESTROY
    }

    /**
     * 绑定生命周期
     */
    fun bindLifecycleClear() {
        if (!isEnabledLifecycleClear()) {
            return
        }
        getTargetLifecycle()?.addObserver(this)
    }

    /**
     * 取消绑定生命周期（如果实现类是自己主动销毁的，需要主动调下本方法）
     */
    fun unBindLifecycleClear() {
        if (!isEnabledLifecycleClear()) {
            return
        }
        getTargetLifecycle()?.removeObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (!isEnabledLifecycleClear()) {
            return
        }
        if (getClearEvent() == event) {
            unBindLifecycleClear()
            onTargetCleared()
        }
    }
}