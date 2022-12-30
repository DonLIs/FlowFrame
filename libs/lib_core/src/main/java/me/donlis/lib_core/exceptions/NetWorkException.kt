package me.donlis.lib_core.exceptions

/**
 * 网络异常 （网络连续导致的异常）
 */
open class NetWorkException : SuperException {

    constructor(message: String, code: Int = 0) : super(message, code)
    constructor(message: String, cause: Throwable?, code: Int = 0) : super(message, cause, code)
}