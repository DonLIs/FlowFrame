package me.donlis.lib_core.component

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import me.donlis.lib_core.common.IComponent
import me.donlis.lib_core.interfaces.LifecycleCleared
import me.donlis.lib_core.ktx.asLifecycle

open class SuperDialog : Dialog, IComponent, LifecycleCleared {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        initView()
    }

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        initView()
    }

    protected open fun initView() {
        val layoutId = getLayoutResId()
        if (layoutId != View.NO_ID) {
            setContentView(layoutId)
        } else {
            createView()?.let {
                setContentView(it)
            }
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Dialog>.onCreate(savedInstanceState)
        onInitializeBefore()
        onInitialize()
        onInitializeAfter()
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
     * 设置边距
     */
    protected fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        window?.apply {
            this.decorView
                .setPadding(left, top, right, bottom)
        }
    }

    /**
     * 设置宽高
     */
    protected fun setSize(
        width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
        height: Int = WindowManager.LayoutParams.WRAP_CONTENT
    ) {
        window?.setLayout(width, height)
    }

    /**
     * 设置方向
     */
    protected fun setGravity(gravity: Int = Gravity.CENTER) {
        window?.apply {
            val layoutParams = this.attributes
            layoutParams.gravity = gravity
            this.attributes = layoutParams
        }
    }

    /**
     * 设置全屏
     * @param color 背景颜色 (默认透明)
     */
    protected fun setFullScreen(@ColorInt color: Int = Color.TRANSPARENT) {
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        setBackgroundDrawableColor(color)
        //一定要在setContentView之后调用，否则无效
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    /**
     * 设置背景
     */
    protected fun setBackgroundDrawable(drawable: Drawable) {
        window?.setBackgroundDrawable(drawable)
    }

    /**
     * 设置背景颜色
     */
    protected fun setBackgroundDrawableColor(@ColorInt color: Int) {
        window?.setBackgroundDrawable(ColorDrawable(color))
    }

    /**
     * 设置背景灰度
     * @param amount
     */
    open fun setBackgroundDimAmount(amount: Float) {
        window?.setDimAmount(amount)
    }

    override fun show() {
        super.show()
        bindLifecycleClear()
    }

    override fun isEnabledLifecycleClear(): Boolean {
        return true
    }

    override fun getLifecycle(): Lifecycle? {
        return context.asLifecycle()
    }

    final override fun getTargetLifecycle(): Lifecycle? {
        return context.asLifecycle()
    }

    override fun onTargetCleared() {
        dismiss()
    }

    override fun dismiss() {
        super.dismiss()
        unBindLifecycleClear()
    }
}