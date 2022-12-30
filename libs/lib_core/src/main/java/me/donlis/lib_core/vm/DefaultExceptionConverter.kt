package me.donlis.lib_core.vm

class DefaultExceptionConverter : ExceptionConverter {
    override fun convert(throwable: Throwable): Throwable {
        return throwable
    }
}