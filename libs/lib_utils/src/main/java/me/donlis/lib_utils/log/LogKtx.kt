package me.donlis.lib_utils.log

fun Any.logI(message: String, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.i(tag, message, filePrinter)
}

fun Any.logV(message: String, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.v(tag, message, filePrinter)
}

fun Any.logW(message: String, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.w(tag, message, filePrinter)
}

fun Any.logD(message: String, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.d(tag, message, filePrinter)
}

fun Any.logE(message: String, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.e(tag, message, filePrinter)
}

fun Any.logE(throwable: Throwable, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.e(tag, throwable, filePrinter)
}

fun Any.logE(message: String, throwable: Throwable, tag: String = this::class.java.simpleName, filePrinter: Boolean = false) {
    LogUtils.e(tag, message, throwable, filePrinter)
}