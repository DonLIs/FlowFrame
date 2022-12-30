package me.donlis.lib_core.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.donlis.lib_core.common.ComponentIntent
import me.donlis.lib_core.common.IvmComponent
import me.donlis.lib_core.interfaces.ILoading

open class SuperFragment : SuperLazyFragment(), IvmComponent, ILoading {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = getLayoutResId()
        if (layoutId != View.NO_ID) {
            return inflater.inflate(layoutId, container, false)
        }
        return createView() ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 获取布局资源id
     */
    protected open fun getLayoutResId(): Int {
        return View.NO_ID
    }

    /**
     * 自定义创建视图内容
     */
    protected open fun createView(): View? {
        return null
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        onInitializeBefore()
        onInitialize()
        onInitializeAfter()
    }

    override fun onAction(action: ComponentIntent) {
        when(action) {
            is ComponentIntent.ShowLoading -> {
                showLoading(action.message)
            }
            is ComponentIntent.DismissLoading -> {
                dismissLoading()
            }
            is ComponentIntent.Toast -> {

            }
        }
    }

    override fun <T : View> findViewById(id: Int): T? {
        return view?.findViewById<T>(id)
    }

    override fun showLoading(message: CharSequence?) {

    }

    override fun dismissLoading() {

    }
}