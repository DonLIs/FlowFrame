package me.donlis.lib_core.support

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

/**
 * Activity生命周期管理
 */
class ActivityLifecycleManager private constructor() : Application.ActivityLifecycleCallbacks {

    companion object {
        val instance: ActivityLifecycleManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityLifecycleManager()
        }
    }

    @Volatile
    private var refCount = 0

    //管理activity的任务栈
    private val existedActivity: Stack<Activity> by lazy {
        Stack<Activity>()
    }

    /**
     * 是否后台
     */
    fun isAppOnBackground(): Boolean {
        return refCount <= 0
    }

    /**
     * 是否前台
     */
    fun isAppOnForeground(): Boolean {
        return refCount > 0
    }

    /**
     * 运行activity数量
     */
    fun getActivityCount(): Int {
        return existedActivity.size
    }

    /**
     * 获取所有运行的activity
     */
    fun getAllActivity(): List<Activity> {
        return existedActivity
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (existedActivity.empty()) {
            null
        } else existedActivity.lastElement()
    }

    /**
     * 上一个Activity
     */
    fun previousActivity(): Activity? {
        return try {
            if (existedActivity.empty()) {
                null
            } else existedActivity[existedActivity.size - 2]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishLastActivity() {
        existedActivity.lastElement()?.let {
            finishActivity(it)
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            existedActivity.remove(activity)
            activity.finish()
        }
    }

    /**
     * 找到任务stack的activity
     */
    fun hasActivity(cls: Class<*>): Boolean {
        //找到任务stack的activity
        for (activity in existedActivity) {
            if (activity.javaClass == cls) {
                return true
            }
        }
        return false
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        val activities = Stack<Activity>()
        //找到任务stack的activity
        for (activity in existedActivity) {
            if (activity.javaClass == cls) {
                activities.add(activity)
            }
        }
        finishAllActivity(activities)
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        finishAllActivity(existedActivity)
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity(existedActivity: Stack<Activity>) {
        var i = 0
        val size = existedActivity.size
        while (i < size) {
            if (null != existedActivity[i]) {
                existedActivity[i]!!.finish()
            }
            i++
        }
        existedActivity.clear()
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {
        refCount++
    }

    override fun onActivityDestroyed(activity: Activity) {
        existedActivity.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        refCount--
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        existedActivity.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {

    }
}