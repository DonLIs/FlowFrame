package me.donlis.lib_core.ktx

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import me.donlis.lib_utils.AppUtils

/**
 * 资源工具类
 */


/**
 * 获取颜色
 */
fun Fragment.getColorById(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(context!!, colorResId)
}


/**
 * 获取图片
 */
fun Fragment.getDrawableById(@DrawableRes drawableRedId: Int): Drawable? {
    return ContextCompat.getDrawable(context!!, drawableRedId)
}


/**
 * 获取颜色
 */
fun Activity.getColorById(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

/**
 * 获取图片
 */
fun Activity.getDrawableById(@DrawableRes drawableRedId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRedId)
}


/**
 * 获取颜色
 */
fun Context.getColorById(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

/**
 * 获取图片
 */
fun Context.getDrawableById(@DrawableRes drawableRedId: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRedId)
}


/**
 * 获取字符串资源
 */
fun Any.getStringById(@StringRes stringResId: Int): String {
    return AppUtils.getApp().getString(stringResId)
}

/**
 * 获取字符串资源
 */
fun Int.toStringRes(): String {
    return AppUtils.getApp().getString(this)
}

/**
 * 获取资源drawable
 * */
fun Int.toDrawableRes(): Drawable? {
    return ContextCompat.getDrawable(AppUtils.getApp(), this)
}

/**
 * 获取资源color
 * */
fun Int.toColorRes(): Int {
    return ContextCompat.getColor(AppUtils.getApp(), this)
}

/**
 * 通过自定义属性-获取DrawableRes
 */
@DrawableRes
fun Context.getDrawableResFromAttr(
    @AttrRes attrResId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int? {
    return try {
        theme.resolveAttribute(attrResId, typedValue, resolveRefs)
        return typedValue.resourceId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 通过自定义属性-获取Drawable
 */
fun Context.getDrawableFromAttr(@AttrRes attrId: Int): Drawable? {
    return try {
        val drawableRes = getDrawableResFromAttr(attrId) ?: return null
        ResourcesCompat.getDrawable(resources, drawableRes, null)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 通过自定义属性-获取ColorRes
 */
@ColorRes
fun Context.getColorResFromAttr(
    @AttrRes attrResId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int? {
    return try {
        theme.resolveAttribute(attrResId, typedValue, resolveRefs)
        return typedValue.resourceId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 通过自定义属性-获取Color
 */
@ColorRes
fun Context.getColorFromAttr(
    @AttrRes attrResId: Int
): Int? {
    return try {
        val colorRes = getColorFromAttr(attrResId) ?: return null
        ResourcesCompat.getColor(resources, colorRes, null)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 通过自定义属性-获取LayoutRes
 */
@LayoutRes
fun Context.getLayoutResFromAttr(
    @AttrRes attrResId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int? {
    return try {
        theme.resolveAttribute(attrResId, typedValue, resolveRefs)
        return typedValue.resourceId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 通过自定义属性-获取Boolean
 */
fun Context.getBooleanResFromAttr(
    @AttrRes attrResId: Int,
    defValue: Boolean = false
): Boolean {
    var attrs: TypedArray? = null
    try {
        attrs = obtainStyledAttributes(null, intArrayOf(attrResId))
        return attrs.getBoolean(0, defValue)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        attrs?.recycle()
    }
    return defValue
}