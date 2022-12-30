package me.donlis.lib_core.ktx

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import me.donlis.lib_utils.AppUtils

/**
 * 复制到剪切板
 */
fun String.copyToClipboard(): Boolean {
    return try {
        //获取剪贴板管理器
        val clipboardManager =
            AppUtils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val clipData = ClipData.newPlainText("Label", this)
        // 将ClipData内容放到系统剪贴板里。
        clipboardManager.setPrimaryClip(clipData)
        true
    } catch (e: Exception) {
        false
    }
}

//</editor-fold>


//判断是否是手机号码
fun String?.isMobileNumber(): Boolean {
    if (this == null) return false
    if (this.length < 7) return false
    if (this.length > 11) return false
    //TODO 简单验证，根据需求再完善..
    return true
}

//判断是否不是手机号码
fun String?.isNotMobileNumber(): Boolean {
    return !isMobileNumber()
}

// 加密手机号码
fun String.encryptMobileNumber(default: String? = this): String? {
    // TODO 待完善：不同国家手机号长度不同
    return if (isMobileNumber()) {
        this.substring(0, 3) + "****" + this.substring(7, length)
    } else {
        default
    }
}


/**
 * 字符拆分为map （内容按指定长度拆分装进map中）
 * @param name Map中Key的名称（name1,name2..）
 * @param itemLength 单个条目长度
 */
fun String.splitToMap(name: String, itemLength: Int): Map<String, String> {
    val map = LinkedHashMap<String, String>()
    if (this.isEmpty()) {
        return map
    }
    if (itemLength < 1) {
        map["${name}1"] = this
        return map
    }
    var index = 0
    val maxCount = (this.length + itemLength - 1) / itemLength
    while (index < maxCount) {
        val startPosition = index * itemLength
        val endPosition = (startPosition + itemLength).coerceAtMost(this.length)
        val text = this.substring(startPosition, endPosition)
        map["$name${index + 1}"] = text
        index++
    }
    return map
}