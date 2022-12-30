package me.donlis.lib_core.vm

interface ExceptionConverter {

    /**
     * 转换
     */
    fun convert(throwable: Throwable): Throwable
}