package me.donlis.lib_core.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import me.donlis.lib_core.utils.FragmentUtils

/**
 * Fragment扩展
 */

/**
 * 展示Fragment
 * @param fragment
 * @param isHideOther 是否隐藏其他Fragment
 * @param isApplyLifecycle 是否应用生命周期
 */
fun FragmentActivity.showFragment(
        fragment: Fragment,
        containerViewId: Int,
        isHideOther: Boolean = false,
        isApplyLifecycle: Boolean = true,
        tag: String? = fragment.javaClass.name
) {
    FragmentUtils.showFragment(fragmentManager = supportFragmentManager,
            fragment = fragment,
            containerViewId = containerViewId,
            isHideOther = isHideOther,
            isApplyLifecycle = isApplyLifecycle,
            tag = tag)
}

/**
 * 展示Fragment
 * @param fragment
 * @param isHideOther 是否隐藏其他Fragment
 * @param isApplyLifecycle 是否应用生命周期
 */
fun Fragment.showFragment(
        fragment: Fragment,
        containerViewId: Int,
        isHideOther: Boolean = false,
        isApplyLifecycle: Boolean = true,
        tag: String? = fragment.javaClass.name
) {
    FragmentUtils.showFragment(fragmentManager = childFragmentManager,
            fragment = fragment,
            containerViewId = containerViewId,
            isHideOther = isHideOther,
            isApplyLifecycle = isApplyLifecycle,
            tag = tag)
}

/**
 * 移除Fragment
 */
fun FragmentActivity.removeFragment(
        fragment: Fragment
) {
    FragmentUtils.removeFragment(supportFragmentManager, fragment)
}

/**
 * 移除Fragment
 */
fun Fragment.removeFragment(
        fragment: Fragment
) {
    FragmentUtils.removeFragment(childFragmentManager, fragment)
}

/**
 * 添加Fragment（只添加）
 * @param fragment
 * @param containerViewId
 * @param isApplyLifecycle 是否应用生命周期
 */
fun FragmentActivity.addFragment(
        fragment: Fragment,
        containerViewId: Int,
        isApplyLifecycle: Boolean = true,
        tag: String? = fragment.javaClass.name
) {
    FragmentUtils.addFragment(fragmentManager = supportFragmentManager,
            fragment = fragment,
            containerViewId = containerViewId,
            isApplyLifecycle = isApplyLifecycle,
            tag = tag)
}

/**
 * 添加Fragment（只添加）
 * @param fragment
 * @param containerViewId
 * @param isApplyLifecycle 是否应用生命周期
 */
fun Fragment.addFragment(
        fragment: Fragment,
        containerViewId: Int,
        isApplyLifecycle: Boolean = true,
        tag: String? = fragment.javaClass.name
) {
    FragmentUtils.addFragment(fragmentManager = childFragmentManager,
            fragment = fragment,
            containerViewId = containerViewId,
            isApplyLifecycle = isApplyLifecycle,
            tag = tag)
}

/**
 * 隐藏Fragment
 */
fun FragmentActivity.hideFragment(
        fragment: Fragment
) {
    FragmentUtils.hideFragment(supportFragmentManager, fragment)
}

/**
 * 隐藏Fragment
 */
fun Fragment.hideFragment(
        fragment: Fragment
) {
    FragmentUtils.hideFragment(childFragmentManager, fragment)
}
