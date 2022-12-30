package me.donlis.lib_core.ktx

import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.AnimRes
import me.donlis.lib_core.utils.UiUtils
import me.donlis.lib_utils.AppUtils

/**
 * Desc: Popup扩展
 */


/**
 * 展示在目标点位置，根据自身宽高自动调整（注意：大尺寸的popup暂未适配）
 * @param root 根视图
 * @param anchorX 锚点X(相对于屏幕)
 * @param anchorY 锚点Y(相对于屏幕)
 * @param popupWindowW popup宽度
 * @param popupWindowW popup高度
 * @param leftTopAnim 动画-左上
 * @param leftBottomAnim 动画-左下
 * @param rightTopAnim 动画-右上
 * @param rightBottomAnim 动画-右下
 */
fun PopupWindow.showAsAutoLocation(
    root: View,
    anchorX: Int,
    anchorY: Int,
    popupWindowW: Int,
    popupWindowH: Int,
    @AnimRes leftTopAnim: Int?,
    @AnimRes leftBottomAnim: Int?,
    @AnimRes rightTopAnim: Int?,
    @AnimRes rightBottomAnim: Int?,
) {
    val screenW = UiUtils.getScreenWidth(AppUtils.getApp())
    val screenH = UiUtils.getScreenHeight(AppUtils.getApp())
    var x = anchorX
    var y = anchorY
    if (popupWindowW + anchorX > screenW) {
        x = anchorX - popupWindowW
    }
    if (popupWindowH + anchorY > screenH) {
        y = anchorY - popupWindowH
    }
    val animStyle = if (x == anchorX && y != anchorY) {
        rightTopAnim
    } else if (x != anchorX && y == anchorY) {
        leftBottomAnim
    } else if (x != anchorX && y != anchorY) {
        leftTopAnim
    } else {
        rightBottomAnim
    }
    if (animStyle != null) {
        animationStyle = animStyle
    }
    showAtLocation(root, Gravity.TOP and Gravity.START, x, y)
}