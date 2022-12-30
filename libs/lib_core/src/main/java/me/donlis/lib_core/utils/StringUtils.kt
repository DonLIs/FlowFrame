package me.donlis.lib_core.utils

import java.math.BigDecimal

/**
 * Desc:字符串工具
 */
object StringUtils {

    /**
     * 超出最大长度后裁剪拼接后缀
     */
    fun maxLengthSuffix(text: CharSequence, maxLength: Int, suffix: String): CharSequence {
        return if (text.length > maxLength) {
            "${text.subSequence(0, maxLength)}$suffix"
        } else {
            text
        }
    }

    /**
     * 转换为多少w（超出1万后转换为多少w）
     */
    fun toW(count: Int, suffix: String = "w"): String {
        return toW(count.toLong(), suffix)
    }

    /**
     * 转换为多少w（超出1万后转换为多少w）
     */
    fun toW(count: Long, suffix: String = "w"): String {
        return toShortCount(count, 9999, suffix, 1)
    }

    /**
     * 转换为多少w（超出1万后转换为多少w）
     */
    fun toW(count: Long, suffix: String = "w", scale: Int): String {
        return toShortCount(count, 9999, suffix, scale)
    }

    /**
     * 转换为短格式的数量
     * @param count 数量
     * @param threshold 超出的阀值
     * @param suffix 超出阀值后的拼接后缀
     * @param scale 保留的位数
     */
    fun toShortCount(count: Long, threshold: Long, suffix: String, scale: Int = 1): String {
        if (count > threshold) {
            return BigDecimal(count).divide(BigDecimal(threshold + 1), scale, BigDecimal.ROUND_DOWN)
                .toString().plus(suffix)
        }
        return count.toString()
    }

    /**
     * 手机号码中间显示星号
     */
    fun showCenterStarPhone(phone: String): String {
        // TODO 过于简单，需要优化
        return if (phone.trim().length > 7) {
            phone.substring(0, 3) + "****" + phone.substring(7, phone.length)
        } else {
            phone.substring(0, 3) + "****"
        }
    }

    /**
     * 银行卡中间显示星号
     */
    fun showCenterStarCard(card: String): String {
        val length: Int = card.length - 8
        if (length > 1) {
            return card.replace("(\\d{4})(\\d{1,})(\\d{4})".toRegex(), "$1****$3")
        }
        return card
    }

    /**
     * 身份证中间显示星号
     */
    fun showCenterIdCard(card: String): String {
        if (card.length != 18) {
            return card
        }
        return "${card.substring(0, 3)}***********${card.substring(14)}"
    }

    /**
     * 获取string 前几个字符的 string
     * value:字
     * long 前几个字符
     */
    fun interceptByteToString(value: String, long: Long): String {
        var valueLength = 0
        val chinese = "[\u4e00-\u9fa5]"
        var text = ""
        for (i in value.indices) {
            val temp = value.substring(i, i + 1)
            valueLength += if (temp.matches(chinese.toRegex())) {
                2
            } else {
                1
            }
            if (valueLength <= long) {
                text += temp
            } else {
                return "$text..."
            }
        }
        return text
    }

    /**
     * 获取字符串有多少个字节
     */
    fun getStringToByte(value: String): Int {
        var valueLength = 0
        val chinese = "[\u4e00-\u9fa5]"
        for (i in value.indices) {
            val temp = value.substring(i, i + 1)
            valueLength += if (temp.matches(chinese.toRegex())) {
                2
            } else {
                1
            }
        }
        return valueLength
    }

    /**
     * 格式化未读数量
     */
    fun formatUnreadCount(count: Long, maxCount: Long = 99, postfix: CharSequence = "+"): String {
        if (count <= 0) {
            return ""
        }
        if (count > maxCount) {
            return "${maxCount}$postfix"
        }
        return count.toString()
    }
}
