package me.donlis.lib_core.ktx

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import me.donlis.lib_core.coroutine.CoroutineUtils.main
import me.donlis.lib_utils.AppUtils

/**
 * TOAST
 */

fun Context.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(applicationContext, message, duration)
}

fun Context.toast(@StringRes messageResId: Int?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(applicationContext, messageResId, duration)
}

fun Fragment.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(this.context?.applicationContext, message, duration)
}

fun Fragment.toast(@StringRes messageResId: Int?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(this.context?.applicationContext, messageResId, duration)
}

fun Any.toast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(null, message, duration)
}

fun Any.toast(@StringRes messageResId: Int?, duration: Int = Toast.LENGTH_SHORT) {
    toastHandler(null, messageResId, duration)
}

/**
 * toast - 文本
 */
private fun toastHandler(context: Context?, message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (message.isNullOrEmpty()) {
        return
    }
    val con = context ?: AppUtils.getApp() ?: return
    try {
        Toast.makeText(con, message, duration).show()
    } catch (e: Exception) {
        Log.e("ToastKtx", "toastHandler() thread:${Thread.currentThread().name} ,e:$e")
        main {
            try {
                Toast.makeText(con, message, duration).show()
            } catch (e: Exception) {
                Log.e("ToastKtx", "toastHandler()2 thread:${Thread.currentThread().name} ,e:$e")
            }
        }
    }
}

/**
 * toast - 资源ID
 */
private fun toastHandler(context: Context?, @StringRes messageResId: Int?, duration: Int = Toast.LENGTH_SHORT) {
    if (messageResId == null) {
        return
    }
    val con = context ?: AppUtils.getApp() ?: return
    toastHandler(con, con.getString(messageResId))
}