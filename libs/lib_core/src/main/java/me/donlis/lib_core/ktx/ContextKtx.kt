package me.donlis.lib_core.ktx

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Desc:Context相关工具
 **/

/**
 * Context转换为Lifecycle
 */
fun Context?.asLifecycle(): Lifecycle? {
    if (this == null) return null
    return when (this) {
        is Lifecycle -> {
            this
        }
        is LifecycleOwner -> {
            this.lifecycle
        }
        is ContextWrapper -> {
            this.baseContext.asLifecycle()
        }
        else -> {
            null
        }
    }
}

