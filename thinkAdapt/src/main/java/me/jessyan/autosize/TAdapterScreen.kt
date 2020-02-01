package me.jessyan.autosize

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import me.jessyan.autosize.bean.DisplayMetricsInfo
import me.jessyan.autosize.bean.ExternalAdaptInfo
import me.jessyan.autosize.core.AdaptConfig
import me.jessyan.autosize.core.CoreAdaptScreen
import me.jessyan.autosize.core.ExternalAdaptManager
import me.jessyan.autosize.internal.CancelAdapt
import me.jessyan.autosize.internal.CustomAdapt
import me.jessyan.autosize.lifecycle.ActivityLifecycleCallbacksImpl
import me.jessyan.autosize.strategy.IAutoAdaptStrategy
import me.jessyan.autosize.utils.Preconditions
import me.jessyan.autosize.utils.ScreenUtils
import java.util.concurrent.ConcurrentHashMap

class TAdapterScreen : IAutoAdaptStrategy {

    lateinit var application: Application
    /**
     * 用来管理外部三方库 [Activity] 的适配
     * */
    val externalAdaptManager = ExternalAdaptManager()
    /**
     * 最初的 [DisplayMetrics]
     * */
    var initDisplayMetrics: DisplayMetrics? = null
    /**
     * 最初的 [Configuration]
     * */
    var initConfiguration: Configuration? = null

    /**
     * 设备的屏幕总宽度, 单位 px
     */
    var screenWidth: Int = 0
    /**
     * 设备的屏幕总高度, 单位 px, 如果 [.isUseDeviceSize] 为 `false`, 屏幕总高度会减去状态栏的高度
     */
    var screenHeight: Int = 0
    /**
     * 状态栏高度, 当 [.isUseDeviceSize] 为 `false` 时, AndroidAutoSize 会将 [.screenHeight] 减去状态栏高度
     * AndroidAutoSize 默认使用 [@ScreenUtils#getStatusBarHeight()][@ScreenUtils.getStatusBarHeight] 方法获取状态栏高度
     * AndroidAutoSize 使用者可使用 [.setStatusBarHeight] 自行设置状态栏高度
     */
    val statusBarHeight: Int = ScreenUtils.statusBarHeight

    /**
     * 屏幕方向
     * */
    val isVertical: Boolean
        get() = application.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * 配置信息
     * */
    var adaptConfig = AdaptConfig()

    /**
     * 缓存信息
     * */
    private val cache = ConcurrentHashMap<String, DisplayMetricsInfo>()

    private var activityLifecycleCallbacks: ActivityLifecycleCallbacksImpl? = null

    private var iAutoAdaptStrategy: IAutoAdaptStrategy? = null

    var isStop: Boolean = true

    fun init(
        application: Application,
        adaptConfig: AdaptConfig? = null,
        iAutoAdaptStrategy: IAutoAdaptStrategy? = null
    ) {
        this.application = application
        this.iAutoAdaptStrategy = iAutoAdaptStrategy
        adaptConfig?.let {
            this.adaptConfig = it
        }

        initScreenParams()

        //保留最初的数据
        initDisplayMetrics()

        getMetaData(application)

        initLifecycle()
        start()
    }

    private fun initDisplayMetrics() {
        if (initDisplayMetrics == null) {
            initDisplayMetrics = DisplayMetrics()
            initDisplayMetrics?.setTo(Resources.getSystem().displayMetrics)
        }

        if (initConfiguration == null) {
            initConfiguration = Configuration()
            initConfiguration?.setTo(Resources.getSystem().configuration)
        }
    }

    private fun initScreenParams() {
        if (screenWidth != 0) return
        val screenSize = ScreenUtils.getScreenSize(application)
        screenWidth = screenSize[0]
        screenHeight = screenSize[1]
    }

    private fun initLifecycle() {
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                if (newConfig != null) {
                    if (newConfig.fontScale > 0) {
                        initDisplayMetrics?.let {
                            it.scaledDensity =
                                Resources.getSystem().displayMetrics.scaledDensity
                        }
                    }

                    val screenSize = ScreenUtils.getScreenSize(application)
                    screenWidth = screenSize[0]
                    screenHeight = screenSize[1]
                }
            }

            override fun onLowMemory() {

            }
        })

        val tempAutoAdaptStrategy = iAutoAdaptStrategy ?: this
        activityLifecycleCallbacks = ActivityLifecycleCallbacksImpl(
            tempAutoAdaptStrategy
        )
    }

    fun release() {
        externalAdaptManager.release()
    }

    fun stop(activity: Activity) {
        if (isStop) return
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        cancelAdapt(activity)
    }

    fun start() {
        if (!isStop) return
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    //默认执行者
    override fun applyAdapt(target: Any, activity: Activity) {
        initScreenParams()
        initDisplayMetrics()

        //检查是否开启了外部三方库的适配模式, 只要不主动调用 ExternalAdaptManager 的方法, 下面的代码就不会执行
        if (externalAdaptManager.isRun) {
            if (externalAdaptManager.isCancelAdapt(target.javaClass)) {
                cancelAdapt(activity)
                return
            } else {
                val info = externalAdaptManager
                    .getExternalAdaptInfoOfActivity(target.javaClass)
                info?.let {
                    adapt(activity, it)
                    return
                }
            }
        }

        //如果 target 实现 CancelAdapt 接口表示放弃适配, 所有的适配效果都将失效
        if (target is CancelAdapt) {
            cancelAdapt(activity)
            return
        }

        //如果 target 实现 CustomAdapt 接口表示该 target 想自定义一些用于适配的参数, 从而改变最终的适配效果
        if (target is CustomAdapt) {
            adapt(activity, target)
        } else {
            adapt(activity)
        }
    }

    /**
     * 取消适配
     * @param activity [Activity]
     */
    fun cancelAdapt(activity: Activity? = null) {
        initDisplayMetrics?.let {
            CoreAdaptScreen.setDensity(
                activity,
                application,
                it
            )
        }

        initConfiguration?.let {
            CoreAdaptScreen.setScreenSizeDp(
                activity = activity,
                application = application,
                screenWidthDp = it.screenWidthDp,
                screenHeightDp = it.screenHeightDp
            )
        }
    }

    /**
     * 这里是今日头条适配方案的核心代码, 核心在于根据当前设备的实际情况做自动计算并转换 [DisplayMetrics.density]、
     * [DisplayMetrics.scaledDensity]、[DisplayMetrics.densityDpi] 这三个值, 额外增加 [DisplayMetrics.xdpi]
     * 以支持单位 `pt`、`in`、`mm`
     *
     * @param activity      [Activity]
     * @param sizeInDp      设计图上的设计尺寸, 单位 dp, 如果 {@param isBaseOnWidth} 设置为 `true`,
     * {@param sizeInDp} 则应该填写设计图的总宽度, 如果 {@param isBaseOnWidth} 设置为 `false`,
     * {@param sizeInDp} 则应该填写设计图的总高度
     * @param isBaseOnWidth 是否按照宽度进行等比例适配, `true` 为以宽度进行等比例适配, `false` 为以高度进行等比例适配
     * @see [今日头条官方适配方案](https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA)
     */
    fun adapt(activity: Activity, sizeInDp: Int, isBaseOnWidth: Boolean) {
        Preconditions.checkNotNull(activity, "activity == null")
        if (sizeInDp == 0) return
        val displayMetricsInfo = getCacheDisplayMetricsInfo(sizeInDp, isBaseOnWidth)
        displayMetricsInfo?.let {
            CoreAdaptScreen.setDensity(
                activity,
                it.density,
                it.densityDpi,
                it.scaledDensity,
                it.xdpi
            )
            CoreAdaptScreen.setScreenSizeDp(
                activity,
                screenWidthDp = it.screenWidthDp,
                screenHeightDp = it.screenHeightDp
            )
        }
    }

    private fun getCacheDisplayMetricsInfo(
        sizeInDp: Int,
        isBaseOnWidth: Boolean
    ): DisplayMetricsInfo? {
        if (initDisplayMetrics == null) return null
        var designSize = if (isBaseOnWidth)
            adaptConfig.designWidthInDp
        else
            adaptConfig.designHeightInDp
        designSize = if (designSize > 0) designSize else sizeInDp

        val screenSize = if (isBaseOnWidth) screenWidth else screenHeight

        val key = (sizeInDp.toString() + "|" + designSize + "|" + isBaseOnWidth + "|"
                + adaptConfig.isUseDeviceSize + "|"
                + initDisplayMetrics!!.scaledDensity + "|"
                + screenSize)

        var displayMetricsInfo = cache[key]
        var targetDensity = 0f
        var targetDensityDpi = 0
        var targetScaledDensity = 0f
        var targetXdpi = 0f
        val targetScreenWidthDp: Int
        val targetScreenHeightDp: Int

        if (displayMetricsInfo == null) {
            targetDensity = if (isBaseOnWidth) {
                screenWidth * 1.0f / sizeInDp
            } else {
                screenHeight * 1.0f / sizeInDp
            }
            val scale = if (adaptConfig.isExcludeFontScale)
                1F
            else
                initDisplayMetrics!!.scaledDensity * 1.0f / initDisplayMetrics!!.density
            targetScaledDensity = targetDensity * scale
            targetDensityDpi = (targetDensity * 160).toInt()

            targetScreenWidthDp = screenWidth / targetDensity.toInt()
            targetScreenHeightDp =
                screenHeight / targetDensity.toInt()

            targetXdpi = if (isBaseOnWidth) {
                screenWidth * 1.0f / designSize
            } else {
                screenHeight * 1.0f / designSize
            }

            displayMetricsInfo = DisplayMetricsInfo(
                targetDensity,
                targetDensityDpi,
                targetScaledDensity,
                targetXdpi,
                targetScreenWidthDp,
                targetScreenHeightDp
            )

            cache[key] = displayMetricsInfo
        }

        return displayMetricsInfo
    }

    /**
     * 使用 [Activity] 或 [@Fragment] 的自定义参数进行适配
     *
     * @param activity    [Activity]
     */
    fun adapt(activity: Activity) {
        var sizeInDp =
            if (adaptConfig.isBaseOnWidth) adaptConfig.designWidthInDp else adaptConfig.designHeightInDp
        adapt(
            activity,
            sizeInDp,
            adaptConfig.isBaseOnWidth
        )
    }

    /**
     * 使用 [Activity] 或 [@Fragment] 的自定义参数进行适配
     *
     * @param activity    [Activity]
     * @param customAdapt [Activity] 或 [@Fragment] 需实现 [CustomAdapt]
     */
    fun adapt(activity: Activity, customAdapt: CustomAdapt) {
        Preconditions.checkNotNull(customAdapt, "customAdapt == null")
        var sizeInDp = customAdapt.sizeInDp

        //如果 CustomAdapt#getSizeInDp() 返回 0, 则使用在 AndroidManifest 上填写的设计图尺寸
        if (sizeInDp <= 0) {
            sizeInDp =
                if (customAdapt.isBaseOnWidth) adaptConfig.designWidthInDp else adaptConfig.designHeightInDp
        }
        adapt(
            activity,
            sizeInDp,
            customAdapt.isBaseOnWidth
        )
    }

    /**
     * 使用外部三方库的 [Activity] 或 [@Fragment] 的自定义适配参数进行适配
     *
     * @param activity          [Activity]
     * @param externalAdaptInfo 三方库的 [Activity] 或 [@Fragment] 提供的适配参数, 需要配合 [ExternalAdaptManager.addExternalAdaptInfoOfActivity]
     */
    fun adapt(activity: Activity, externalAdaptInfo: ExternalAdaptInfo) {
        Preconditions.checkNotNull(externalAdaptInfo, "externalAdaptInfo == null")
        var sizeInDp = externalAdaptInfo.sizeInDp

        //如果 ExternalAdaptInfo#getSizeInDp() 返回 0, 则使用在 AndroidManifest 上填写的设计图尺寸
        if (sizeInDp <= 0) {
            sizeInDp =
                if (externalAdaptInfo.isBaseOnWidth) adaptConfig.designWidthInDp else adaptConfig.designHeightInDp
        }
        adapt(
            activity,
            sizeInDp,
            externalAdaptInfo.isBaseOnWidth
        )
    }

    /**
     * 获取使用者在 AndroidManifest 中填写的 Meta 信息
     *
     * Example usage:
     * <pre>
     * <meta-data android:name="design_width_in_dp" android:value="360"></meta-data>
     * <meta-data android:name="design_height_in_dp" android:value="640"></meta-data>
    </pre> *
     *
     * @param context [Context]
     */
    private fun getMetaData(context: Context) {
        Thread(Runnable {
            val packageManager = context.packageManager
            val applicationInfo: ApplicationInfo?
            try {
                applicationInfo = packageManager.getApplicationInfo(
                    context
                        .packageName, PackageManager.GET_META_DATA
                )
                if (applicationInfo?.metaData != null) {
                    if (applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH_IN_DP)) {
                        adaptConfig.designWidthInDp =
                            applicationInfo.metaData.get(KEY_DESIGN_WIDTH_IN_DP) as Int
                    }
                    if (applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT_IN_DP)) {
                        adaptConfig.designHeightInDp =
                            applicationInfo.metaData.get(KEY_DESIGN_HEIGHT_IN_DP) as Int
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }).start()
    }

    companion object {
        private val Tag = TAdapterScreen::class.java.canonicalName
        private const val KEY_DESIGN_WIDTH_IN_DP = "design_width_in_dp"
        private const val KEY_DESIGN_HEIGHT_IN_DP = "design_height_in_dp"

        @Volatile
        private var sInstance: TAdapterScreen? = null

        fun getInstance(): TAdapterScreen {
            if (sInstance == null) {
                synchronized(TAdapterScreen::class.java) {
                    if (sInstance == null) {
                        sInstance =
                            TAdapterScreen()
                    }
                }
            }
            return sInstance!!
        }
    }

}