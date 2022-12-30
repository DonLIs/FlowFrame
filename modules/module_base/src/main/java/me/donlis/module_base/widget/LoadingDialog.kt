package me.donlis.module_base.widget

import android.content.Context
import me.donlis.base.R
import me.donlis.module_base.core.BaseDialog

/**
 * 加载弹窗
 */
open class LoadingDialog(context: Context, private val cancelable: Boolean = true) : BaseDialog(context,
    R.style.base_dialog_transparent) {

    override fun onInitialize() {
        super.onInitialize()
        setCancelable(cancelable)
    }

    override fun getLayoutResId(): Int {
        return R.layout.base_dialog_loading
    }
}