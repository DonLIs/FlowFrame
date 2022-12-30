package me.donlis.lib_core.common

import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle

interface IComponent {

    /**
     * 初始化前
     */
    fun onInitializeBefore() {}

    /**
     * 初始化时
     */
    fun onInitialize() {}

    /**
     * 初始化后
     */
    fun onInitializeAfter() {}

    fun getLifecycle(): Lifecycle?

    fun <T : View> findViewById(@IdRes id: Int): T?

}