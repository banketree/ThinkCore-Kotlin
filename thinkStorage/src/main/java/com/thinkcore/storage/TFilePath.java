package com.thinkcore.storage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

public class TFilePath {
    public static final String PATH_IMAGE = "image";
    // public static final String PATH_INDIVIDUAL = "individal";
    public static final String PATH_AUDIO = "audio";
    public static final String PATH_VIDEO = "video";
    public static final String PATH_DOWNLOAD = "download";
    public static final String PATH_CACHE = "cache";
    public static final String PATH_SPLIT = File.separator;

    protected String appName = "";
    private Context context;

    public TFilePath(Context context) {
        this.context = context;

        appName = context
                .getPackageName();

        boolean result = false;
        if (!getExternalStorage().isDirectoryExists(getExternalAppDir()))
            result = getExternalStorage().createDirectory(appName);

        if (!getExternalStorage().isDirectoryExists(getExternalImageDir()))
            result = getExternalStorage().createDirectory(
                    appName + PATH_SPLIT + PATH_IMAGE);

        if (!getExternalStorage().isDirectoryExists(getExternalAudioDir()))
            result = getExternalStorage().createDirectory(
                    appName + PATH_SPLIT + PATH_AUDIO);

        if (!getExternalStorage().isDirectoryExists(getExternalVideoDir()))
            result = getExternalStorage().createDirectory(
                    appName + PATH_SPLIT + PATH_VIDEO);

        if (!getExternalStorage().isDirectoryExists(getExternalDownloadDir()))
            result = getExternalStorage().createDirectory(
                    appName + PATH_SPLIT + PATH_DOWNLOAD);

        if (!getExternalStorage().isDirectoryExists(getExternalCacheDir()))
            result = getExternalStorage().createDirectory(
                    appName + PATH_SPLIT + PATH_CACHE);

        result = getInternalStorage().createDirectory(PATH_IMAGE);
        result = getInternalStorage().createDirectory(PATH_AUDIO);
        result = getInternalStorage().createDirectory(PATH_VIDEO);
        result = getInternalStorage().createDirectory(PATH_DOWNLOAD);
        result = getInternalStorage().createDirectory(PATH_CACHE);
    }

    public InternalStorage getInternalStorage() {
        return TStorage.getInstance().getInternalStorage(this.context);
    }

    public ExternalStorage getExternalStorage() {
        return TStorage.getInstance().getExternalStorage();
    }

    public String getImageDirName() {
        return appName + PATH_SPLIT + PATH_IMAGE + PATH_SPLIT;
    }

    public String getInterImageDir() {
        return getInterAppDir() + PATH_SPLIT + PATH_IMAGE + PATH_SPLIT;
    }

    public String getExternalImageDir() {
        return getExternalAppDir() + PATH_SPLIT + PATH_IMAGE;
    }

    // public String getIndividualDir() {
    // return appName + PATH_SPLIT + PATH_IMAGE_INDIVIDUAL;
    // }
    //
    // public String getExternalIndividualDir() {
    // return getExternalAppDir() + PATH_SPLIT + PATH_IMAGE_INDIVIDUAL;
    // }

    public String getAudioDirName() {
        return appName + PATH_SPLIT + PATH_AUDIO + PATH_SPLIT;
    }

    public String getInterAudioDir() {
        return getInterAppDir() + PATH_SPLIT + PATH_AUDIO + PATH_SPLIT;
    }

    public String getExternalAudioDir() {
        return getExternalAppDir() + PATH_SPLIT + PATH_AUDIO;
    }

    public String getVideoDirName() {
        return appName + PATH_SPLIT + PATH_VIDEO + PATH_SPLIT;
    }

    public String getInterVideoDir() {
        return getInterAppDir() + PATH_SPLIT + PATH_VIDEO + PATH_SPLIT;
    }

    public String getExternalVideoDir() {
        return getExternalAppDir() + PATH_SPLIT + PATH_VIDEO;
    }

    public String getDownloadDirName() {
        return appName + PATH_SPLIT + PATH_DOWNLOAD + PATH_SPLIT;
    }

    public String getInterDownloadDir() {
        return getInterAppDir() + PATH_SPLIT + PATH_DOWNLOAD + PATH_SPLIT;
    }

    public String getExternalDownloadDir() {
        return getExternalAppDir() + PATH_SPLIT + PATH_DOWNLOAD;
    }

    public String getCacheDir() {
        return appName + PATH_SPLIT + PATH_CACHE + PATH_SPLIT;
    }

    public String getInterCacheDir() {
        return getInterAppDir() + PATH_SPLIT + PATH_CACHE + PATH_SPLIT;
    }

    public String getExternalCacheDir() {
        return getExternalAppDir() + PATH_SPLIT + PATH_CACHE;
    }

    public String getInterAppDir() {
        File file = this.context.getCacheDir();
        if (file == null) {
            String path = "/data/data/" + this.context.getPackageName() + PATH_SPLIT;
            return path;
        }
        return file.getParent();
    }

    public String getExternalAppDir() {
        return TStorage.getInstance().getExternalStorage().getPath()
                + PATH_SPLIT + appName;
    }

    // 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String get4_4Path(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat
                && DocumentsContract.isDocumentUri(context,
                uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri,
                        null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri,
                        selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 根据图片的Uri获取图片的绝对路径(已经适配多种API)
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @TargetApi(19)
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {id};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }
}
