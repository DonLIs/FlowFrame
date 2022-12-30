package me.donlis.lib_utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * apk安装工具
 */
object InstallUtils {

    /**
     * 获取安装apk意图
     */
    fun getInstallAppIntent(context: Context, file: File): Intent? {
        if (!file.exists()) {
            //安装失败文件不存在
            return null
        }
        //安装
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //判断是否是android 7.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //赋予临时权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            //7.0获取存储文件的uri
            val uri: Uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".fileProvider", file)
            //设置dataAndType
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            val resInfoList: List<ResolveInfo> = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (!resInfoList.isNullOrEmpty()) {
                for (resolveInfo in resInfoList) {
                    val packageName = resolveInfo.activityInfo.packageName
                    context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        return intent
    }
}