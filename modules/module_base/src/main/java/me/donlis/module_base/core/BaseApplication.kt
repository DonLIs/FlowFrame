package me.donlis.module_base.core

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.facebook.stetho.Stetho
import com.liulishuo.filedownloader.FileDownloader
import com.wanjian.cockroach.Cockroach
import me.donlis.base.BuildConfig
import me.donlis.lib_core.component.SuperApplication
import me.donlis.lib_core.support.ActivityLifecycleManager
import me.donlis.lib_utils.DeviceUtil
import me.donlis.lib_utils.PathUtils
import me.donlis.lib_utils.log.LogUtils
import me.donlis.module_base.config.ProjectUtil
import me.donlis.module_base.support.CockroachHandler
import me.donlis.module_base.support.PathManager
import java.io.File


open class BaseApplication : SuperApplication(),
    Application.ActivityLifecycleCallbacks by ActivityLifecycleManager.instance {

    override fun onInitialize() {
        super.onInitialize()

        DeviceUtil.init()

        registerActivityLifecycleCallbacks(this)

        initCockroach()

        // 网络调试
        initStetho()

        if (ProjectUtil.isDebug()) {
            // 阿里路由
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }

        //阿里路由
        ARouter.init(this)

        initLog()

        FileDownloader.setup(this)

        //initPlugin()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        try {
            Glide.get(this).trimMemory(level)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        try {
            Glide.get(this).clearMemory()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(this)
        ARouter.getInstance().destroy()
    }

    /**
     * 初始化Stetho（网络调试）
     */
    private fun initStetho() {
        if (ProjectUtil.isDebug()) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
        }
    }

    /**
     * 异常处理
     */
    private fun initCockroach() {
        Cockroach.install(this, CockroachHandler())
    }

    private fun initLog() {
        val config = LogConfiguration.Builder()
            .logLevel(
                if (BuildConfig.DEBUG) LogLevel.ALL // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                else LogLevel.ERROR
            )
            .tag("X_LOG") // 指定 TAG，默认为 "X-LOG"
            .enableThreadInfo() // 允许打印线程信息，默认禁止
            .enableStackTrace(2) // 允许打印深度为 2 的调用栈信息，默认禁止
            .enableBorder() // 允许打印日志边框，默认禁止
            .build()

        val filePrinter: Printer = FilePrinter.Builder(PathManager.getPath(PathUtils.getInternalAppFilesPath(this), File.separator + "log")) // 指定保存日志文件的路径
            .fileNameGenerator(DateFileNameGenerator()) // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
            .backupStrategy(NeverBackupStrategy()) // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
            .cleanStrategy(FileLastModifiedCleanStrategy(30 * 24 * 60 * 60 * 1000L)) // 指定日志文件清除策略，默认为 NeverCleanStrategy()
            .build()

        LogUtils.init(config, filePrinter)
    }

}