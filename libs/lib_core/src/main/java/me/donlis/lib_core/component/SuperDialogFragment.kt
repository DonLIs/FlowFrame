package me.donlis.lib_core.component

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import me.donlis.lib_core.common.ComponentIntent
import me.donlis.lib_core.common.IvmComponent
import me.donlis.lib_core.interfaces.ILoading
import me.donlis.lib_core.interfaces.LifecycleCleared
import me.donlis.lib_core.ktx.toast
import me.donlis.lib_core.support.LifecycleTask

open class SuperDialogFragment : DialogFragment(), IvmComponent, ILoading,
    LifecycleTask.LifecycleTaskOwner, LifecycleCleared {

    // 生命周期任务
    private val lifecycleTask: LifecycleTask.LifecycleTaskImpl by lazy {
        LifecycleTask.LifecycleTaskImpl(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = getLayoutResId()
        return if (layoutId != View.NO_ID) {
            layoutInflater.inflate(layoutId, container, false)
        } else {
            createView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindLifecycleClear()
        onInitializeBefore()
        onInitialize()
        onInitializeAfter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.apply {
            setBackgroundDrawable(getDialogBackground())
        }
        super.onActivityCreated(savedInstanceState)
        initStyle()
    }

    /**
     * 初始化样式
     */
    protected open fun initStyle() {
        // 初始化宽高大小
        setSize(getWidth(), getHeight())
        // 默认方向
        setGravity(getGravity())
    }

    /**
     * 创建内容视图
     */
    protected open fun createView(): View? {
        return null
    }

    /**
     * 创建内容视图 根据布局ID
     */
    @LayoutRes
    protected open fun getLayoutResId(): Int {
        return View.NO_ID
    }

    /**
     * 是否可点击阴影部分取消
     */
    protected open fun getCanceled(): Boolean {
        return true
    }

    /**
     * 展示宽度 (可重写该方法修改)
     */
    protected open fun getWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    /**
     * 展示高度 (可重写该方法修改)
     */
    protected open fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 内容方向 (可重写该方法修改)
     */
    protected open fun getGravity(): Int {
        return Gravity.NO_GRAVITY
    }

    /**
     * 背景图
     */
    protected open fun getDialogBackground(): Drawable {
        return ColorDrawable(0x00000000)
    }

    override fun <T : View> findViewById(id: Int): T? {
        return view?.findViewById<T>(id)
    }

    /**
     * 设置窗口大小
     */
    protected fun setSize(width: Int, height: Int) {
        dialog?.window?.setLayout(width, height)
    }

    /**
     * 设置方向
     */
    protected fun setGravity(gravity: Int) {
        dialog?.window?.setGravity(gravity)
    }

    override fun onAction(action: ComponentIntent) {
        when (action) {
            is ComponentIntent.ShowLoading -> {
                showLoading(message = action.message)
            }
            is ComponentIntent.DismissLoading -> {
                dismissLoading()
            }
            is ComponentIntent.Toast -> {
                toast(action.message)
            }
            else -> {

            }
        }
    }

    override fun showLoading(message: CharSequence?) {
    }

    override fun dismissLoading() {
    }

    override fun getLifecycleTask(): LifecycleTask {
        return lifecycleTask
    }

    final override fun getTargetLifecycle(): Lifecycle? {
        return activity?.lifecycle
    }

    override fun onTargetCleared() {
        dismissAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        unBindLifecycleClear()
    }

}
