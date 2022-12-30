package me.donlis.module_base.core

import android.view.WindowManager
import me.donlis.base.R
import me.donlis.lib_core.common.ComponentIntent
import me.donlis.lib_core.component.SuperDialogFragment
import me.donlis.lib_core.ktx.singleClick
import me.donlis.lib_utils.KeyboardUtils
import me.donlis.module_base.widget.LoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 基础组件-弹窗Fragment
 */
open class BaseDialogFragment : SuperDialogFragment() {

    private var loadingDialog: LoadingDialog? = null

    override fun onInitialize() {
        super.onInitialize()
        view?.singleClick {
            KeyboardUtils.hideSoftInput(it)
        }
    }

    override fun onInitializeAfter() {
        super.onInitializeAfter()
        registerEventBus()
    }

    /**
     * 主题样式
     */
    override fun getTheme(): Int {
        return R.style.base_dialog
    }

    /**
     * 展示宽度 (可重写该方法修改宽度)
     */
    override fun getWidth(): Int {
        // 默认宽度全屏
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    /**
     * 展示高度 (可重写该方法修改高度)
     */
    override fun getHeight(): Int {
        // 默认高度自适应
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun showLoading(message: CharSequence?) {
        super.showLoading(message)
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(requireContext())
        }
        if (loadingDialog?.isShowing != true) {
            loadingDialog?.show()
        }
    }

    override fun dismissLoading() {
        super.dismissLoading()
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    /**
     * 来自ViewModel的命令
     */
    override fun onAction(action: ComponentIntent) {
        super.onAction(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
        loadingDialog = null
        unRegisterEventBus()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Any) {
    }

    private fun registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }
}