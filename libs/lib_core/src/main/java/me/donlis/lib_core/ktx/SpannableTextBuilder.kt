package me.donlis.lib_core.ktx

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 * Created by Max on 3/8/21 10:16 AM
 * Desc:可扩展文本
 */
class SpannableTextBuilder(private val textView: TextView) {

    private val spannableBuilder: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
    }

    /**
     * 添加一段文本
     * @param text 文本
     * @param textColor 文本颜色
     * @param backgroundColor 背景颜色
     * @param textSize 文本大小
     * @param textStyle 文本样式
     * @param underline 是否有下划线
     * @param clickListener 点击事件
     */
    fun appendText(text: String, @ColorInt textColor: Int? = null, @ColorInt backgroundColor: Int? = null, textSize: Int? = null, textStyle: Int? = null, underline: Boolean? = null, clickListener: ((String) -> Unit)? = null): SpannableTextBuilder {
        val start = spannableBuilder.length
        spannableBuilder.append(text)
        val end = spannableBuilder.length

        // 文本颜色
        if (textColor != null) {
            spannableBuilder.setSpan(ForegroundColorSpan(textColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 文本背景颜色
        if (backgroundColor != null) {
            spannableBuilder.setSpan(BackgroundColorSpan(backgroundColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 文本大小
        if (textSize != null) {
            spannableBuilder.setSpan(AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 文本样式
        if (textStyle != null) {
            spannableBuilder.setSpan(StyleSpan(textStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 下划线
        if (underline == true) {
            spannableBuilder.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 点击事件
        if (clickListener != null) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            val clickableSpan = TextClickableSpan(clickListener, text, textColor
                    ?: textView.currentTextColor, underline ?: false)
            spannableBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

    /**
     * 添加图片
     * @param drawable 图片
     * @param clickListener 点击事件
     */
    fun appendDrawable(@DrawableRes drawable: Int, clickListener: ((Int) -> Unit)?): SpannableTextBuilder {
        // 需要时再完善
        val start = spannableBuilder.length
        spannableBuilder.append("[icon}")
        val end = spannableBuilder.length

        // 图片
        val imageSpan: ImageSpan = VerticalImageSpan(textView.context, drawable)
        spannableBuilder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 点击事件
        if (clickListener != null) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            val clickableSpan = DrawableClickableSpan(clickListener, drawable)
            spannableBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

    fun build(): SpannableStringBuilder {
        return spannableBuilder
    }

    /**
     * 应用
     */
    fun apply() {
        textView.text = spannableBuilder
    }

    /**
     * 文本点击
     */
    class TextClickableSpan(private val clickListener: ((String) -> Unit)? = null, private val text: String, private val textColor: Int, private val underline: Boolean) : ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener?.invoke(text)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = textColor
            ds.isUnderlineText = underline
        }
    }


    /**
     * 图片点击
     */
    class DrawableClickableSpan(private val clickListener: ((Int) -> Unit)? = null, private val drawable: Int) : ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener?.invoke(drawable)
        }
    }
}


/**
 * 快速构建生成器
 */
fun TextView.spannableBuilder(): SpannableTextBuilder {
    return SpannableTextBuilder(this)
}

class VerticalImageSpan : ImageSpan {
    constructor(drawable: Drawable) : super(drawable)
    constructor(context: Context, resourceId: Int) : super(context, resourceId)

    /**
     * update the text line height
     */
    override fun getSize(
        paint: Paint, text: CharSequence?, start: Int, end: Int,
        fontMetricsInt: Paint.FontMetricsInt?
    ): Int {
        val drawable = drawable
        val rect = drawable.bounds
        if (fontMetricsInt != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.descent - fmPaint.ascent
            val drHeight = rect.bottom - rect.top
            val centerY = fmPaint.ascent + fontHeight / 2
            fontMetricsInt.ascent = centerY - drHeight / 2
            fontMetricsInt.top = fontMetricsInt.ascent
            fontMetricsInt.bottom = centerY + drHeight / 2
            fontMetricsInt.descent = fontMetricsInt.bottom
        }
        return rect.right
    }

    /**
     * see detail message in android.text.TextLine
     *
     * @param canvas the canvas, can be null if not rendering
     * @param text   the text to be draw
     * @param start  the text start position
     * @param end    the text end position
     * @param x      the edge of the replacement closest to the leading margin
     * @param top    the top of the line
     * @param y      the baseline
     * @param bottom the bottom of the line
     * @param paint  the work paint
     */
    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        val drawable = drawable
        canvas.save()
        val fmPaint = paint.fontMetricsInt
        val fontHeight = fmPaint.descent - fmPaint.ascent
        val centerY = y + fmPaint.descent - fontHeight / 2
        val transY = centerY - (drawable.bounds.bottom - drawable.bounds.top) / 2
        canvas.translate(x, transY.toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }
}