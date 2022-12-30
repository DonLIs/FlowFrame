package me.donlis.lib_utils.log

import android.util.Log
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.Printer

object LogUtils {

    private val androidPrinter: Printer = AndroidPrinter(true) // 通过 android.util.Log 打印日志的打印器

    private val consolePrinter: Printer = ConsolePrinter() // 通过 System.out 打印日志到控制台的打印器

    private var filePrinter: Printer? = null

    fun init(config: LogConfiguration, filePrinter: Printer) {
        LogUtils.filePrinter = filePrinter
        XLog.init( // 初始化 XLog
            config,  // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
            androidPrinter,
            consolePrinter,
            filePrinter
        )
    }

    fun e(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.ERROR, tag, message, filePrinter)
    }

    fun e(tag: String, throwable: Throwable, filePrinter: Boolean = false) {
        val cause = Log.getStackTraceString(throwable)
        if (cause.isEmpty()) {
            return
        }
        e(tag, cause, filePrinter)
    }

    fun e(tag: String, message: String?, throwable: Throwable, filePrinter: Boolean = false) {
        val cause = Log.getStackTraceString(throwable)
        if (message == null && cause.isEmpty()) {
            return
        }
        e(tag, message + "\t\t" + cause, filePrinter)
    }

    fun d(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.DEBUG, tag, message, filePrinter)
    }

    fun i(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.INFO, tag, message, filePrinter)
    }

    fun v(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.VERBOSE, tag, message, filePrinter)
    }

    fun w(tag: String, message: String, filePrinter: Boolean = false) {
        log(Log.WARN, tag, message, filePrinter)
    }

    /**
     * 输出日志
     */
    fun log(level: Int = Log.INFO, tag: String?, message: String?, filePrinter: Boolean = false) {
        if (tag.isNullOrEmpty()) {
            return
        }
        if (message.isNullOrEmpty()) {
            return
        }
        // 输出控制台
        logConsole(level, tag, message)
        // 输出文件
        if (filePrinter) {
            logFile(level, tag, message)
        }
    }

    /**
     * 输出到控制台
     */
    fun logConsole(level: Int = Log.INFO, tag: String, message: String) {
        androidPrinter.println(level, tag, message)
    }

    /**
     * 输出到文件
     */
    fun logFile(level: Int = Log.INFO, tag: String, message: String) {
        filePrinter?.println(level, tag, message)
    }

}