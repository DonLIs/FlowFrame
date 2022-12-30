package me.donlis.lib_core.ktx

import me.donlis.lib_core.utils.StringUtils


/**
 * 转换W（万）
 * @param placeholder 占位
 */
fun Long?.toW(placeholder: String = "0"): String {
    if (this == null || this == 0L) {
        return placeholder
    }
    return StringUtils.toShortCount(count = this, threshold = 9999, scale = 1, suffix = "w")
}

/**
 * 转换K（千）
 * @param placeholder 占位
 */
fun Long?.toK(placeholder: String = "0"): String {
    if (this == null || this == 0L) {
        return placeholder
    }
    return StringUtils.toShortCount(count = this, threshold = 999, scale = 1, suffix = "k")
}

/**
 * 转换简单数量
 * @param threshold 阀值大小 如：9，999，99999
 * @param suffix 后缀 如：千，万，w
 */
fun Long.toShortCount(threshold: Long = 9999, suffix: String = "万", scale: Int = 1): String {
    return StringUtils.toShortCount(count = this, threshold = threshold, suffix = suffix, scale = scale)
}
