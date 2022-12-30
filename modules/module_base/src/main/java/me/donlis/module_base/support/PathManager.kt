package me.donlis.module_base.support

import me.donlis.lib_utils.AppUtils
import me.donlis.lib_utils.PathUtils
import java.io.File

object PathManager {

    /**
     * 获取网络请求缓存文件路径
     */
    fun getHttpCachePath(): String {
        return getPath(getAppPrivateCachePath(), "http")
    }

    /**
     * 获取应用缓存路径（目录路径）（私有/内置）
     */
    fun getAppPrivateCachePath(): String {
        // /data/data/package/cache
        return PathUtils.getInternalAppCachePath(AppUtils.getApp())
    }

    /**
     * 获取路径-根据目录与文件名称
     * @param directory 目录
     * @param name 文件名or目录名
     * @return 文件路径
     */
    fun getPath(directory: String, name: String): String {
        return if (directory.endsWith(File.separator)) {
            directory + name
        } else {
            directory + File.separator + name
        }
    }

}