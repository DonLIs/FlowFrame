package me.donlis.lib_core.exceptions

/**
 * 代码处理异常（解析json出错）
 */
open class CodeException : SuperException {
    constructor(message: String?, code: Int = 0) : super(message, code)
    constructor(message: String?, cause: Throwable?, code: Int = 0) : super(message, cause, code)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}