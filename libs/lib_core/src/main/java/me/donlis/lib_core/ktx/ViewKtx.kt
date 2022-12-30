package me.donlis.lib_core.ktx

import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.TextView
import androidx.core.view.ScrollingView
import me.donlis.lib_core.utils.ShapeViewOutlineProvider
import me.donlis.lib_utils.ColorUtils


/**
 * 展示or隐藏
 */
fun View.visibleOrGone(isShow: Boolean) {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 展示or隐藏
 */
inline fun View.visibleOrGone(show: View.() -> Boolean = { true }) {
    visibility = if (show(this)) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 展示or不可见
 */
inline fun View.visibleOrInvisible(show: View.() -> Boolean = { true }) {
    visibility = if (show(this)) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

/**
 * 点击事件
 */
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 点击事件
 */
fun <T : View> T.singleClick(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

/**
 * 设置View圆角矩形
 */
fun <T : View> T.roundCorner(corner: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (outlineProvider == null || outlineProvider !is ShapeViewOutlineProvider.Round) {
            outlineProvider = ShapeViewOutlineProvider.Round(corner.toFloat())
        } else if (outlineProvider != null && outlineProvider is ShapeViewOutlineProvider.Round) {
            (outlineProvider as ShapeViewOutlineProvider.Round).corner = corner.toFloat()
        }
        clipToOutline = true
    }
}

/**
 * 设置View为圆形
 */
fun <T : View> T.circle() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (outlineProvider == null || outlineProvider !is ShapeViewOutlineProvider.Circle) {
            outlineProvider = ShapeViewOutlineProvider.Circle()
        }
        clipToOutline = true
    }
}

fun View.getBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.translate(scrollX.toFloat(), scrollY.toFloat())
    draw(canvas)
    return bitmap
}

/**
 * 设置边距
 */
fun View?.setMargin(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        start?.let {
            this.marginStart = start
        }
        top?.let {
            this.topMargin = top
        }
        end?.let {
            this.marginEnd = end
        }
        bottom?.let {
            this.bottomMargin = bottom
        }
    }
}


/**
 * 设置内边距
 */
fun View?.setPadding2(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    if (this == null) return
    this.setPadding(
        start ?: paddingStart,
        top ?: paddingTop,
        end ?: paddingEnd,
        bottom ?: paddingBottom
    )
}

/**
 * 描边宽度
 */
fun TextView.strokeWidth(width: Float) {
    this.paint?.style = Paint.Style.FILL_AND_STROKE
    this.paint?.strokeWidth = width
    this.invalidate()
}

/**
 * 模拟点击并取消
 */
fun ScrollingView.simulateClickAndCancel() {
    val view = this as? View ?: return
    val downEvent = MotionEvent.obtain(
        System.currentTimeMillis(), System.currentTimeMillis(), MotionEvent.ACTION_DOWN,
        (view.right - view.left) / 2f, (view.bottom - view.top) / 2f, 0
    )
    view.dispatchTouchEvent(downEvent)
    val cancelEvent = MotionEvent.obtain(
        System.currentTimeMillis(), System.currentTimeMillis(), MotionEvent.ACTION_CANCEL,
        (view.right - view.left) / 2f, (view.bottom - view.top) / 2f, 0
    )
    view.dispatchTouchEvent(cancelEvent)
}

/**
 * 使用灰色滤镜
 */
fun View.applyGrayFilter(isGray: Boolean) {
    try {
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(if (isGray) 0f else 1f)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 给view 动态设置shape
 */
fun View.setShape(
    colorList: List<String>?,
    radius: Int,
    strokeWidth: Int = 0,
    strokeColor: Int = Color.TRANSPARENT,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT
) {
    try {
        val colors = colorList?.map {
            ColorUtils.string2Int(it)
        }?.toIntArray()

        if (colors == null || colors.isEmpty()) {
            return
        }

        if (colors.size == 1) {
            GradientDrawable().apply {
                setColor(colors.first())
                this.orientation = orientation
                cornerRadius = radius.toPX().toFloat()

                if (strokeWidth > 0 && strokeColor != Color.TRANSPARENT) {
                    setStroke(strokeWidth.toPX(), strokeColor)
                }
            }.also {
                background = it
            }
        } else {
            GradientDrawable(orientation, colors).apply {
                cornerRadius = radius.toPX().toFloat()

                if (strokeWidth > 0 && strokeColor != Color.TRANSPARENT) {
                    setStroke(strokeWidth.toPX(), strokeColor)
                }
            }.also {
                background = it
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}


