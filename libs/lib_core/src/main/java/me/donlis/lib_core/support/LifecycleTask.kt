package me.donlis.lib_core.support

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.concurrent.ConcurrentHashMap

/**
 * 生命周期任务
 */
interface LifecycleTask {

    /**
     * 获取生命周期
     */
    fun getLifecycle(): Lifecycle?

    /**
     * 获取当前生命周期事件位置
     */
    fun getCurrentEvent(): Lifecycle.Event?

    /**
     * 添加一个生命周期任务
     * @param event 生命周期事件（若当前生命周期[getCurrentEvent]与该[event]一致时，会立即执行该任务[block]）
     * @param tag 任务标记 (相同的tag会覆盖)
     * @param block 可运行函数
     */
    fun addLifecycleTask(event: Lifecycle.Event, tag: String? = null, block: () -> Unit)

    /**
     * 取消指定生命周期任务
     * @param tag 任务标记
     * @param event 生命周期事件（ON_ANY：清空所有）
     */
    fun cancelLifecycleTask(event: Lifecycle.Event, tag: String? = null)

    /**
     * 取消指定生命周期任务
     * @param block 任务
     * @param event 生命周期事件（ON_ANY：清空所有）
     */
    fun cancelLifecycleTask(event: Lifecycle.Event, block: () -> Unit)

    /**
     * 主动执行生命周期任务
     */
    fun runLifecycleTask(event: Lifecycle.Event)

    /**
     * LifecycleTask 所有者
     */
    interface LifecycleTaskOwner {
        fun getLifecycleTask(): LifecycleTask
    }


    /**
     * 默认实现
     */
    companion object
    class LifecycleTaskImpl(private val lifecycle: Lifecycle) :
            LifecycleTask {

        private val taskMap: ConcurrentHashMap<Lifecycle.Event, TaskGroup> by lazy {
            ConcurrentHashMap<Lifecycle.Event, TaskGroup>()
        }

        private var lifecycleObserver: LifecycleObserver? = null

        /**
         * 当前事件
         */
        private var currentEvent: Lifecycle.Event? = null

        init {
            checkLifecycleEvent()
        }

        /**
         * 检查绑定生命周期事件
         */
        private fun checkLifecycleEvent() {
            if (lifecycleObserver == null) {
                lifecycleObserver = object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
                    fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
                        currentEvent = event
                        taskMap.remove(event)?.handlerTask()
                        if (event == Lifecycle.Event.ON_DESTROY) {
                            release()
                        }
                    }
                }.apply {
                    getLifecycle()?.addObserver(this)
                }
            }
        }

        /**
         * 释放
         */
        private fun release() {
            taskMap.clear()
            lifecycleObserver?.let {
                getLifecycle()?.removeObserver(it)
                lifecycleObserver = null
            }
        }

        override fun getLifecycle(): Lifecycle? {
            return lifecycle
        }

        override fun getCurrentEvent(): Lifecycle.Event? {
            return currentEvent
        }

        override fun addLifecycleTask(event: Lifecycle.Event, tag: String?, block: () -> Unit) {
            checkLifecycleEvent()
            if (!taskMap.containsKey(event)) {
                taskMap[event] = TaskGroup()
            }
            taskMap[event]?.addTask(tag, block)
            if (currentEvent == event) {
                runLifecycleTask(event)
            }
        }

        override fun cancelLifecycleTask(event: Lifecycle.Event, tag: String?) {
            if (event == Lifecycle.Event.ON_ANY) {
                taskMap.clear()
            } else {
                taskMap[event]?.removeTask(tag)
            }
        }

        override fun cancelLifecycleTask(event: Lifecycle.Event, block: () -> Unit) {
            if (event == Lifecycle.Event.ON_ANY) {
                taskMap.clear()
            } else {
                taskMap[event]?.removeTask(block)
            }
        }

        override fun runLifecycleTask(event: Lifecycle.Event) {
            taskMap.remove(event)?.handlerTask()
        }

        // 任务组
        class TaskGroup : HashMap<String, (() -> Unit)>() {

            /**
             * 添加任务
             */
            fun addTask(tag: String? = null, task: (() -> Unit)) {
                put(tag ?: "default", task)
            }

            /**
             * 执行任务
             */
            fun handlerTask() {
                values.forEach {
                    it.invoke()
                }
            }

            /**
             * 移除任务
             */
            fun removeTask(tag: String? = null) {
                if (tag == null) {
                    clear()
                    return
                }
                keys.forEach {
                    if (it == tag) {
                        remove(it)
                        return
                    }
                }
            }

            /**
             * 移除任务
             */
            fun removeTask(task: () -> Unit) {
                keys.forEach {
                    if (getValue(it) == task) {
                        remove(it)
                        return
                    }
                }
            }
        }
    }
}