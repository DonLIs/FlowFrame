package me.donlis.lib_utils.log

interface ILog {

    /**
     * 默认日志Tag
     */
    fun getLogTag(): String {
        return this::class.java.simpleName
    }

    fun logI(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtils.i(tag, message, filePrinter)
    }

    fun logV(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtils.v(tag, message, filePrinter)
    }

    fun logW(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtils.w(tag, message, filePrinter)
    }

    fun logD(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtils.d(tag, message, filePrinter)
    }

    fun logE(message: String, tag: String = getLogTag(), filePrinter: Boolean = false) {
        LogUtils.e(tag, message, filePrinter)
    }

    fun logE(
        throwable: Throwable,
        tag: String = getLogTag(),
        filePrinter: Boolean = false
    ) {
        LogUtils.e(tag, throwable, filePrinter)
    }

    fun logE(
        message: String,
        throwable: Throwable,
        tag: String = getLogTag(),
        filePrinter: Boolean = false
    ) {
        LogUtils.e(tag, message, throwable, filePrinter)
    }

}