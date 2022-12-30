package me.donlis.lib_core.component

import android.app.ActivityManager
import android.content.Context
import android.os.Process
import androidx.annotation.CallSuper
import androidx.multidex.MultiDexApplication
import me.donlis.lib_utils.AppUtils

open class SuperApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)
        if (isMainProcess(this)) {
            onInitialize()
        }
    }

    @CallSuper
    protected open fun onInitialize() {

    }

    /**
     * 判断是否是当前进程
     */
    open fun isMainProcess(context: Context): Boolean {
        val pid = Process.myPid()
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        if (activityManager?.runningAppProcesses == null) {
            return false
        }
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return context.applicationInfo.packageName == appProcess.processName
            }
        }
        return false
    }

}