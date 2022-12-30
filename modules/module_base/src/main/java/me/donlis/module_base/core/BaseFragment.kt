package me.donlis.module_base.core

import com.alibaba.android.arouter.launcher.ARouter
import me.donlis.lib_core.component.SuperFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *
 */
open class BaseFragment : SuperFragment() {

    override fun onInitializeBefore() {
        super.onInitializeBefore()
        ARouter.getInstance().inject(this)
    }

    override fun onInitializeAfter() {
        super.onInitializeAfter()
        registerEventBus()
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterEventBus()
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Object) {

    }

}