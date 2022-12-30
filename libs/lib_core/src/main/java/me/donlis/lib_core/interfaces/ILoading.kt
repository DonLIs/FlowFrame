package me.donlis.lib_core.interfaces

interface ILoading {

    fun showLoading(message: CharSequence? = null)

    fun dismissLoading()

}