package me.donlis.lib_core.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlin.math.min

/**
 *
 */
class ShapeViewOutlineProvider {

    /**
     * Desc:圆角
     */
    class Round(var corner: Float) : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                    0,
                    0,
                    view.width,
                    view.height,
                    corner
            )
        }
    }

    /**
     * Desc:圆形
     */
    class Circle : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val min = min(view.width, view.height)
            val left = (view.width - min) / 2
            val top = (view.height - min) / 2
            outline.setOval(left, top, min, min)
        }
    }
}