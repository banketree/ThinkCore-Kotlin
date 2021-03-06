package com.thinkcore.activity

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.util.Log
import java.io.File
import java.lang.RuntimeException
import java.util.*

object TActivityUtils {
    private val TAG = TActivityUtils::class.java.simpleName

//    fun jumpToActivity(
//        context: Context,
//        targetClass: Class<*>? = null,
//        targetIntent: Intent? = null,
//        bundle: Bundle?
//    ) {
//        var actionIntent = targetIntent
//        if (targetClass == null && targetIntent == null) {
//            throw RuntimeException("targetClass and targetIntent null")
//        } else if (targetIntent == null) {
//            actionIntent = Intent(context, targetClass)
//        }
//
//        bundle?.let {
//            actionIntent?.putExtras(it)
//        }
//
//        context.startActivity(actionIntent)
//    }
//
//    @JvmOverloads
//    fun jumpToNewTopActivity(
//        context: Context,
//        targetClass: Class<*>? = null,
//        targetIntent: Intent? = null,
//        bundle: Bundle? = null
//    ) {
//        var actionIntent = targetIntent
//        if (targetClass == null && targetIntent == null) {
//            throw RuntimeException("targetClass and targetIntent null")
//        } else if (targetIntent == null) {
//            actionIntent = Intent(context, targetClass)
//        }
//
//        actionIntent?.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        bundle?.let {
//            actionIntent?.putExtras(it)
//        }
//
//        context.startActivity(actionIntent)
//    }
//
//    //TAppActivity
//    fun jumpToActivityForResult(
//        activity: TAppActivity,
//        targetClass: Class<*>? = null,
//        targetIntent: Intent? = null,
//        bundle: Bundle? = null
//        , resultId: Int = Random().nextInt(100000) + Random().nextInt(10000)
//        , iActivityResult: IActivityResult? = null
//    ) {
//        var actionIntent = targetIntent
//        if (targetClass == null && targetIntent == null) {
//            throw RuntimeException("targetClass and targetIntent null")
//        } else if (targetIntent == null) {
//            actionIntent = Intent(activity, targetClass)
//        }
//
//        iActivityResult?.let {
//            activity.iActivityResult[resultId] = it
//        }
//        bundle?.let {
//            actionIntent?.putExtras(it)
//        }
//
//        activity.startActivityForResult(actionIntent, resultId)
//    }

    // 跳转到系统短信Activity
    fun jumpToSystemSMSActivity(context: Context, number: String) {
        val mIntent = Intent(Intent.ACTION_VIEW)
        mIntent.putExtra("address", number)
        mIntent.type = "vnd.android-dir/mms-sms"
        context.startActivity(mIntent)
    }

    // 跳转到另一个apk中Activity new android:exported="true"
    // ComponentName("C的包名", "C的包名+C");
//    fun jumpToActivity(
//        context: Context,
//        componentName: ComponentName
//    ) {
//        val mIntent = Intent()
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        mIntent.component = componentName
//        mIntent.action = "android.intent.action.VIEW"
//        context.startActivity(mIntent)
//    }

    /**
     * 回到home，后台运行
     *
     * @param context
     */
    fun jumpToHomeActivity(context: Context) {
        val mHomeIntent = Intent(Intent.ACTION_MAIN)
        mHomeIntent.addCategory(Intent.CATEGORY_HOME)
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        context.startActivity(mHomeIntent)
    }

    /*
    * 跳转到网络设置
    * */
    fun jumpToNetworkSettingActivity(context: Context) {
        var intent: Intent? = null

        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//hasHoneycomb
                intent = Intent(
                    Settings.ACTION_WIRELESS_SETTINGS
                )
            } else {
                intent = Intent()
                val comp = ComponentName(
                    "com.android.settings",
                    "com.android.settings.WirelessSettings"
                )
                intent.component = comp
                intent.action = "android.intent.action.VIEW"
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "open network settings failed, please check...")
            e.printStackTrace()
        }

    }

    /*
    * 跳转到系统相册
    * */
    fun jumpToSystemLocPickImageActivity(
        activity: TAppActivity,
        iActivityResult: IActivityResult
    ) {
        val random = Random()
        val resultId = random.nextInt(10000)
        activity.iActivityResult[resultId] = iActivityResult
        jumpToSystemLocPickImageActivity(activity, resultId)
    }

    fun jumpToSystemLocPickImageActivity(
        activity: Activity,
        requestCode: Int
    ) {
        var intent: Intent? = null
        intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(intent, requestCode)
    }

    /*
    * 跳转到拍照
    * */
    fun jumpToSystemCameraPickImageActivity(
        activity: TAppActivity,
        iActivityResult: IActivityResult
    ) {
        val random = Random()
        val resultId = random.nextInt(10000)
        activity.iActivityResult[resultId] = iActivityResult
        jumpToSystemCameraPickImageActivity(activity, resultId)
    }

    /*
    * 跳转拍照+选择相册
    * */
    fun jumpToSystemCameraPickImageActivity(
        activity: Activity,
        requestCode: Int
    ) {
        var intent: Intent? = null
        intent = Intent("android.media.action.IMAGE_CAPTURE")
        activity.startActivityForResult(intent, requestCode)
    }

    /*
    * 跳转到系统拨号
    * */
    fun jumpToSystemDialActivity(context: Context, number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /*
     * 跳转到系统电话
     * */
    fun jumpToSystemCallActivity(context: Context, number: String) {
        val i = Intent()
        i.action = Intent.ACTION_CALL
        i.data = Uri.parse("tel:$number")
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    /*
    * 跳转到系统短信
    * */
    fun jumpToSystemMessageActivity(
        context: Context,
        number: String
    ) {
        val smsToUri = Uri.parse("smsto://$number")
        val i = Intent(Intent.ACTION_SENDTO, smsToUri)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun jumpToSystemInstallApkActivity(
        context: Context,
        apkPath: String
    ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(
            Uri.fromFile(File(apkPath)),
            "application/vnd.android.package-archive"
        )
        context.startActivity(intent)
    }

    fun jumpToSystemDownloadApk(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val data = Uri.parse(Html.fromHtml(url).toString())
        intent.data = data
        intent.setPackage("com.google.android.browser")
        intent.addCategory("android.intent.category.BROWSABLE")
        intent.component = ComponentName(
            "com.android.browser",
            "com.android.browser.BrowserActivity"
        )
        context.startActivity(intent)
    }

    fun jumpToSystemShareText(context: Context, content: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, content)
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
        // startActivity(Intent.createChooser(sendIntent,
        // getResources().getText(R.string.send_to)));
    }

    fun jumpToSystemShareImage(context: Context, imageUri: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/*"
        context.startActivity(shareIntent)
        // startActivity(Intent.createChooser(shareIntent,
        // getResources().getText(R.string.send_to)));
    }

    fun jumpToSystemShareImages(
        context: Context,
        imageUris: ArrayList<Uri>
    ) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND_MULTIPLE
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        shareIntent.type = "image/*"
        context.startActivity(shareIntent)
        // startActivity(Intent.createChooser(shareIntent,
        // "Share images to.."));
    }

    // http://blog.csdn.net/waylife/article/details/44315103
    fun createShortCut(
        context: Context, shortcutname: String, iconId: Int,
        action: String, shortData: String, packageString: String,
        tagerClass: String
    ) {// 创建快捷方式的Intent
        if (hasInstallShortcut(context, shortcutname))
            return
        val shortcutintent = Intent(
            "com.android.launcher.action.INSTALL_SHORTCUT"
        )
        shortcutintent.putExtra("duplicate", false)// 不允许重复创建
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname)// 需要现实的名称
        val iconParcelable = Intent.ShortcutIconResource.fromContext(
            context, iconId
        )// 快捷图片
        shortcutintent.putExtra(
            Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            iconParcelable
        ) // 点击快捷图片，运行的程序主入口

        val intent = Intent(action)
        // intent.setAction(Intent.ACTION_MAIN);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // intent.setComponent(component);
        intent.setClassName(packageString, tagerClass)
        intent.putExtra("shortData", shortData)

        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent)
        shortcutintent.putExtra("shortData", shortData)
        context.sendBroadcast(shortcutintent) // 发送广播。OK
    }

    fun delShortcut(
        context: Context, shortcutname: String, action: String,
        packageString: String, tagerClass: String
    ) {
        val shortcut = Intent(
            "com.android.launcher.action.UNINSTALL_SHORTCUT"
        )
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutname)
        val comp = ComponentName(packageString, tagerClass)
        shortcut.putExtra(
            Intent.EXTRA_SHORTCUT_INTENT,
            Intent(action).setComponent(comp)
        )
        context.sendBroadcast(shortcut)
    }

    fun hasInstallShortcut(context: Context, name: String): Boolean {
        var hasInstall = false

        var AUTHORITY = "com.android.launcher.settings"
        val systemversion = Build.VERSION.SDK_INT
        if (systemversion < 8) {
            AUTHORITY = "com.android.launcher2.settings"
        } else if (systemversion < 19) {
            AUTHORITY = "com.android.launcher2.settings"
        } else {// 4.4以及以上
            AUTHORITY = "com.android.launcher3.settings"
        }

        val CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY
                    + "/favorites?notify=true"
        )

        val cursor = context.contentResolver.query(
            CONTENT_URI,
            arrayOf("title"), "title=?", arrayOf(name), null
        )

        if (cursor != null && cursor.count > 0) {
            hasInstall = true
        }

        cursor?.close()

        return hasInstall
    }


}
