package me.donlis.module_base.core

import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import com.alibaba.android.arouter.launcher.ARouter
import me.donlis.lib_core.common.ComponentIntent
import me.donlis.lib_core.component.SuperActivity
import me.donlis.lib_utils.KeyboardUtils
import me.donlis.module_base.widget.LoadingDialog
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CancelAdapt
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class BaseActivity : SuperActivity() {

    protected var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onInitializeBefore() {
        super.onInitializeBefore()
        ARouter.getInstance().inject(this)
        initOrientation()
    }

    override fun onInitialize() {
        super.onInitialize()
    }

    override fun onInitializeAfter() {
        super.onInitializeAfter()
        registerEventBus()
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterEventBus()
        dismissLoading()
        loadingDialog = null
    }

    override fun onAction(action: ComponentIntent) {
        super.onAction(action)
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

    /**
     * 初始化方向
     */
    protected open fun initOrientation() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            // 默认模式时强制设为竖屏方向
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /**
     * 隐藏软键盘
     */
    protected open fun hideSoftInput() {
        KeyboardUtils.hideSoftInput(this)
    }

    override fun showLoading(message: CharSequence?) {
        super.showLoading(message)
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
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

    override fun getResources(): Resources {
        if (this !is CancelAdapt && Looper.myLooper() == Looper.getMainLooper()) {
            try {
                // 目前发现DialogFragment中：xml设置的大小和代码转dp的大小不一致，貌似是代码转换方式未进行单位适配，所以通过这种方式解决（解决方案来自单位适配组件官方提供）
                AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return super.getResources()
    }

}