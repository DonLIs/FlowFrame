package me.donlis.module_base.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import me.donlis.base.R
import me.donlis.lib_core.component.SuperDialog

/**
 * 基础组件-弹窗
 */
open class BaseDialog : SuperDialog {

    private var onWindowDismissListener: (() -> Unit)? = null

    constructor(context: Context) : this(context, R.style.base_dialog)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isNeedFullScreen()) {
            // 是否全屏
            setFullScreen()
        }
        if (isBottomStyle()) {
            // 是否底部对齐样式
            setGravity(Gravity.BOTTOM)
            setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        super.onCreate(savedInstanceState)
    }

    /**
     * 是否底部对齐风格
     */
    protected open fun isBottomStyle(): Boolean {
        return false
    }

    /**
     * 是否需要全屏
     */
    protected open fun isNeedFullScreen(): Boolean {
        return false
    }

    /**
     * 设置背景是否有灰度
     * @param enabled true:设置灰度（默认0.5f）
     */
    open fun setBackgroundDimEnabled(enabled: Boolean) {
        window?.setDimAmount(if (enabled) {
            window?.attributes?.dimAmount ?: 0.5f
        } else {
            0f
        })
    }

    open fun getActivity(): Activity? {
        return getActivity(context)
    }

    private  fun getActivity(context: Context?): Activity? {
        return when (context) {
            null -> {
                null
            }
            is Activity -> {
                context
            }
            is ContextWrapper -> {
                getActivity(context.baseContext)
            }
            else -> null
        }
    }

    override fun dismiss() {
        super.dismiss()
        onWindowDismissListener?.invoke()
        onWindowDismissListener = null
    }

    fun setOnDismissListener(listener: (() -> Unit)?) {
        this.onWindowDismissListener = listener
    }
}