package com.thinkcore.storage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

public class TStorageUtils {
    public static String TAG = TStorageUtils.class.getSimpleName();
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    // SDcard 操作
    public static boolean isExternalStorageWrittenable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static boolean checkAvailableStorage() {
        Log.d(TAG, "checkAvailableStorage E");

        if (getAvailableExternalStorage() < 1024 * 1024 * 10) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否存在外部存储设备
     *
     * @return 如果不存在返回false
     */
    public static boolean isExternalStoragePresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查如果外部存储器是内置的或是可移动的。
     *
     * @return 如果外部存储是可移动的(就像一个SD卡)返回为 true,否则false。
     */
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {//hasGingerbread
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /*
     * 获取 SD 卡内存
     */
    public static long getAvailableExternalStorage() {
        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        Log.v(TAG, "getAvailableStorage. storageDirectory : "
                + storageDirectory);

        try {
            StatFs stat = new StatFs(storageDirectory);
            long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat
                    .getBlockSize());
            Log.v(TAG, "getAvailableStorage. avaliableSize : " + avaliableSize);
            return avaliableSize;
        } catch (RuntimeException ex) {
            Log.e(TAG, "getAvailableStorage - exception. return 0");
            return 0;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalStorageSize() {
        if (isExternalStoragePresent()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }

        return 0;// 这里返回错误信息
    }

    /**
     * Calculates the free memory of the device. This is based on an inspection
     * of the filesystem, which in android devices is stored in RAM.
     *
     * @return Number of bytes available.
     */
    public static long getAvailableInternalMemorySize() {
        final File path = Environment.getDataDirectory();
        final StatFs stat = new StatFs(path.getPath());
        final long blockSize = stat.getBlockSize();
        final long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Calculates the total memory of the device. This is based on an inspection
     * of the filesystem, which in android devices is stored in RAM.
     *
     * @return Total number of bytes.
     */
    public static long getTotalInternalMemorySize() {
        final File path = Environment.getDataDirectory();
        final StatFs stat = new StatFs(path.getPath());
        final long blockSize = stat.getBlockSize();
        final long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    // /////////////////////////////////////////////////////
    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context
                .checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
