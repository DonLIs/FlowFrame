package me.donlis.lib_core.ktx

import me.donlis.lib_core.interfaces.ILoading

/**
 * 主线程 - 展示
 */
fun ILoading?.showLoadingInMain(message: CharSequence? = null) {
    main {
        this@showLoadingInMain?.showLoading(message)
    }
}

/**
 * 主线程 - 销毁
 */
fun ILoading?.dismissLoadingInMain() {
    main {
        this@dismissLoadingInMain?.dismissLoading()
    }
}