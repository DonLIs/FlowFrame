package me.donlis.lib_core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle


/**
 * Fragment工具类
 */
object FragmentUtils {

    /**
     * 添加Fragment
     * @param fragment
     * @param containerViewId
     * @param isApplyLifecycle 是否应用生命周期
     */
    fun addFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            containerViewId: Int,
            isApplyLifecycle: Boolean = true,
            tag: String? = fragment.javaClass.name
    ) {
        if (fragment.isAdded) return
        fragmentManager.beginTransaction().apply {
            add(containerViewId, fragment, tag)
            if (isApplyLifecycle) {
                setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
        }.commitAllowingStateLoss()
    }

    /**
     * 移除Fragment
     * @param fragment
     */
    fun removeFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment
    ) {
        if (!fragment.isAdded) {
            return
        }
        fragmentManager.beginTransaction().remove(fragment)
                .commitAllowingStateLoss()
    }

    /**
     * 展示并添加Fragment
     * @param fragment
     * @param isHideOther 是否隐藏其他Fragment
     * @param isApplyLifecycle 是否应用生命周期
     * @param containerViewId
     */
    fun showFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            containerViewId: Int,
            isHideOther: Boolean = false,
            isApplyLifecycle: Boolean = true,
            tag: String? = fragment.javaClass.name
    ) {
        fragmentManager.beginTransaction().apply {
            if (!fragment.isAdded) {
                add(containerViewId, fragment, tag)
            }
            if (isHideOther) {
                fragmentManager.fragments.filter { it != fragment }.forEach {
                    hide(it)
                    if (isApplyLifecycle) {
                        setMaxLifecycle(it, Lifecycle.State.STARTED)
                    }
                }
            }
            show(fragment)
            if (isApplyLifecycle) {
                setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
            }
            commitAllowingStateLoss()
        }
    }

    /**
     * 隐藏Fragment
     * @param fragment
     */
    fun hideFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment
    ) {
        if (fragment.isAdded && !fragment.isHidden) {
            fragmentManager.beginTransaction().apply {
                hide(fragment)
                setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                commitAllowingStateLoss()
            }
        }
    }

    /**
     * 替换Fragment
     * @param fragment
     * @param containerViewId
     */
    fun replaceFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            containerViewId: Int,
            tag: String? = fragment.javaClass.name
    ) {
        fragmentManager.beginTransaction().replace(containerViewId, fragment, tag)
                .commitAllowingStateLoss()
    }
}