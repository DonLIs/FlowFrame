package me.donlis.lib_core.ktx

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText


/**
 * 设置editText输入监听
 * @param onChanged 改变事件
 */
inline fun EditText.setOnInputChangedListener(
        /**
         * @param Int：当前长度
         * @return 是否接受此次文本的改变
         */
        crossinline onChanged: (Int).() -> Boolean) {
    this.addTextChangedListener(object : TextWatcher {

        var flag = false

        override fun afterTextChanged(p0: Editable?) {
            if (flag) {
                return
            }
            if (!onChanged(p0?.length ?: 0)) {
                flag = true
                this@setOnInputChangedListener.setText(this@setOnInputChangedListener.getTag(1982329101) as? String)
                this@setOnInputChangedListener.setSelection(this@setOnInputChangedListener.length())
                flag = false
            } else {
                flag = false
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            this@setOnInputChangedListener.setTag(1982329101, p0?.toString())
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}

/**
 * 切换密码可见度
 */
fun EditText.switchPasswordVisibility(visibility: Boolean) {
    transformationMethod = if (!visibility) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

}

/**
 * 设置输入长度限制过滤器
 */
fun EditText.setMaxLengthFilter(maxLength: Int) {
    this.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
}


/**
 * 设置输入功能是否启用（不启用就相当于TextView）
 */
fun EditText.setInputEnabled(isEnabled: Boolean) {
    if (isEnabled) {
        isFocusable = true
        isFocusableInTouchMode = true
        isClickable = true
    } else {
        isFocusable = false
        isFocusableInTouchMode = false
        isClickable = false
        keyListener = null
    }
}