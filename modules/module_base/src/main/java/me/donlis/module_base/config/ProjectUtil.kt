package me.donlis.module_base.config

import me.donlis.base.BuildConfig


object ProjectUtil {

    /**
     * 是否Debug环境
     */
    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    /**
     * 是否Release环境
     */
    fun isRelease(): Boolean {
        return BuildConfig.BUILD_TYPE == "release"
    }

}