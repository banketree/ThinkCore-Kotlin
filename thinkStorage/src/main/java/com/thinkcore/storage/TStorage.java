package com.thinkcore.storage;

import android.content.Context;

/**
 * Singleton class that supply all possible storage options.<br>
 * <br>
 * <b>Permissions:</b>
 * <ul>
 * <li>android.permission.WRITE_EXTERNAL_STORAGE</li>
 * <li>android.permission.READ_EXTERNAL_STORAGE</li>
 * </ul>
 *
 * @author Roman Kushnarenko - sromku (sromku@gmail.com)
 */
public class TStorage {

    private static InternalStorage mInternalStorage = null;
    private static ExternalStorage mExternalStorage = null;

    private static TStorage that = null;
    private static StorageConfiguration mStorageConfiguration;

    private TStorage() {
        // set default configuration
        mStorageConfiguration = new StorageConfiguration.Builder().build();

        mInternalStorage = new InternalStorage();
        mExternalStorage = new ExternalStorage();
    }

    public static TStorage getInstance() {
        if (that == null) {
            that = new TStorage();
        }
        return that;
    }

    /**
     * The type of the storage. <br>
     * Possible options:
     * <ul>
     * <li>{@link StorageType#INTERNAL}</li>
     * <li>{@link StorageType#EXTERNAL}</li>
     * </ul>
     *
     * @author sromku
     */
    public enum StorageType {
        INTERNAL, EXTERNAL
    }

    /**
     * Get internal storage. The files and folders will be persisted on device
     * memory. The internal storage is good for saving <b>private and secure</b>
     * data.<br>
     * <br>
     * <b>Important:
     * <ul>
     * <li>When the device is low on internal storage space, Android may delete
     * these cache files to recover space.</li>
     * <li>You should always maintain the cache files yourself and stay within a
     * reasonable limit of space consumed, such as 1MB.</li>
     * <li>When the user uninstalls your application, these files are removed.</li>
     * </b>
     * </ul>
     * <i>http://developer.android.com/guide/topics/data/data-storage.html#
     * filesInternal</i>
     *
     * @return {@link InternalStorage}
     */
    public InternalStorage getInternalStorage(Context context) {
        mInternalStorage.initActivity(context);
        return mInternalStorage;
    }

    /**
     * Get external storage. <br>
     *
     * @return {@link ExternalStorage}
     */
    public ExternalStorage getExternalStorage() {
        return mExternalStorage;
    }

    /**
     * Check whereas the external storage is writable. <br>
     *
     * @return <code>True</code> if external storage writable, otherwise return
     * <code>False</code>
     */
    public boolean isExternalStorageWritable() {
        return mExternalStorage.isWritable();
    }

    public StorageConfiguration getConfiguration() {
        return mStorageConfiguration;
    }

    /**
     * Set and update the storage configuration
     *
     * @param configuration
     */
    public static void updateConfiguration(StorageConfiguration configuration) {
        if (that == null) {
            throw new RuntimeException(
                    "First instantiate the Storage and then you can update the configuration");
        }
        mStorageConfiguration = configuration;
    }

    /**
     * Set the configuration to default
     */
    public static void resetConfiguration() {
        StorageConfiguration configuration = new StorageConfiguration.Builder()
                .build();
        mStorageConfiguration = configuration;
    }

}
