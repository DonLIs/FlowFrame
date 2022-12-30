package me.donlis.module_base.support

import android.os.Handler
import android.os.Looper
import com.wanjian.cockroach.ExceptionHandler
import me.donlis.lib_core.ktx.toast
import me.donlis.lib_utils.log.ILog
import me.donlis.module_base.config.ProjectUtil

/**
 * 异常捕捉 - handler
 */
class CockroachHandler : ExceptionHandler(), ILog {

    override fun onUncaughtExceptionHappened(thread: Thread?, throwable: Throwable?) {
        //捕获异常，可能运行在非UI线程
        try {
            //记录日志
            throwable?.let {
                logE(throwable = it, filePrinter = true)
            }

            if (ProjectUtil.isDebug()) {
                Handler(Looper.getMainLooper()).post {
                    toast("捕获到导致崩溃的异常")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBandageExceptionHappened(throwable: Throwable?) {
        //打印警告级别log
        try {
            //记录日志
            throwable?.let {
                logE(throwable = it, filePrinter = true)
            }

            if (ProjectUtil.isDebug()) {
                Handler(Looper.getMainLooper()).post {
                    throwable?.message?.let {
                        toast(it)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onEnterSafeMode() {

    }

    override fun onMayBeBlackScreen(e: Throwable?) {
        super.onMayBeBlackScreen(e)
        //可能黑屏
        try {
            //记录日志
            e?.let {
                logE(throwable = it, filePrinter = true)
            }

            if (ProjectUtil.isDebug()) {
                Handler(Looper.getMainLooper()).post {
                    toast("黑屏")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}