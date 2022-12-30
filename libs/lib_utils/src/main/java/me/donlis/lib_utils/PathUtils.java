package me.donlis.lib_utils;

import android.app.Application;
import android.os.Build;
import android.os.Environment;


/**
 * getRootPath                    : 获取根路径
 * getDataPath                    : 获取数据路径
 * getDownloadCachePath           : 获取下载缓存路径
 * getInternalAppDataPath         : 获取内存应用数据路径
 * getInternalAppCodeCacheDir     : 获取内存应用代码缓存路径
 * getInternalAppCachePath        : 获取内存应用缓存路径
 * getInternalAppDbsPath          : 获取内存应用数据库路径
 * getInternalAppDbPath           : 获取内存应用数据库路径
 * getInternalAppFilesPath        : 获取内存应用文件路径
 * getInternalAppSpPath           : 获取内存应用 SP 路径
 * getInternalAppNoBackupFilesPath: 获取内存应用未备份文件路径
 * getExternalStoragePath         : 获取外存路径
 * getExternalMusicPath           : 获取外存音乐路径
 * getExternalPodcastsPath        : 获取外存播客路径
 * getExternalRingtonesPath       : 获取外存铃声路径
 * getExternalAlarmsPath          : 获取外存闹铃路径
 * getExternalNotificationsPath   : 获取外存通知路径
 * getExternalPicturesPath        : 获取外存图片路径
 * getExternalMoviesPath          : 获取外存影片路径
 * getExternalDownloadsPath       : 获取外存下载路径
 * getExternalDcimPath            : 获取外存数码相机图片路径
 * getExternalDocumentsPath       : 获取外存文档路径
 * getExternalAppDataPath         : 获取外存应用数据路径
 * getExternalAppCachePath        : 获取外存应用缓存路径
 * getExternalAppFilesPath        : 获取外存应用文件路径
 * getExternalAppMusicPath        : 获取外存应用音乐路径
 * getExternalAppPodcastsPath     : 获取外存应用播客路径
 * getExternalAppRingtonesPath    : 获取外存应用铃声路径
 * getExternalAppAlarmsPath       : 获取外存应用闹铃路径
 * getExternalAppNotificationsPath: 获取外存应用通知路径
 * getExternalAppPicturesPath     : 获取外存应用图片路径
 * getExternalAppMoviesPath       : 获取外存应用影片路径
 * getExternalAppDownloadPath     : 获取外存应用下载路径
 * getExternalAppDcimPath         : 获取外存应用数码相机图片路径
 * getExternalAppDocumentsPath    : 获取外存应用文档路径
 * getExternalAppObbPath          : 获取外存应用 OBB 路径
 * 路径 工具类   By https://github.com/Blankj/AndroidUtilCode  -> PathUtils.java
 */
public class PathUtils {

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the path of /system.
     *
     * @return the path of /system
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    public static String getDownloadCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package.
     *
     * @return the path of /data/data/package
     */
    public static String getInternalAppDataPath(Application application) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return application.getApplicationInfo().dataDir;
        }
        return application.getDataDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/code_cache.
     *
     * @return the path of /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir(Application application) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return application.getApplicationInfo().dataDir + "/code_cache";
        }
        return application.getCodeCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/cache.
     *
     * @return the path of /data/data/package/cache
     */
    public static String getInternalAppCachePath(Application application) {
        return application.getCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/databases.
     *
     * @return the path of /data/data/package/databases
     */
    public static String getInternalAppDbsPath(Application application) {
        return application.getApplicationInfo().dataDir + "/databases";
    }

    /**
     * Return the path of /data/data/package/databases/name.
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(Application application, String name) {
        return application.getDatabasePath(name).getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/files.
     *
     * @return the path of /data/data/package/files
     */
    public static String getInternalAppFilesPath(Application application) {
        return application.getFilesDir().getAbsolutePath();
    }

    /**
     * Return the path of /data/data/package/shared_prefs.
     *
     * @return the path of /data/data/package/shared_prefs
     */
    public static String getInternalAppSpPath(Application application) {
        return application.getApplicationInfo().dataDir + "shared_prefs";
    }

    /**
     * Return the path of /data/data/package/no_backup.
     *
     * @return the path of /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath(Application application) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return application.getApplicationInfo().dataDir + "no_backup";
        }
        return application.getNoBackupFilesDir().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents";
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalCacheDir().getParentFile().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath(Application application) {
        if (isExternalStorageDisable()) return "";
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExternalAppDocumentsPath(Application application) {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //noinspection ConstantConditions
            return application.getExternalFilesDir(null).getAbsolutePath() + "/Documents";
        }
        //noinspection ConstantConditions
        return application.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath(Application application) {
        if (isExternalStorageDisable()) return "";
        return application.getObbDir().getAbsolutePath();
    }

    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
