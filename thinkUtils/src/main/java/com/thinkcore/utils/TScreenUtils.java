package com.thinkcore.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class TScreenUtils {
    public static String TAG = TPackageUtils.class.getSimpleName();

    /**
     * Returns a String representation of the content of a
     * {@link Display} object.
     *
     * @param context Context for the application being reported.
     * @return A String representation of the content of the default Display of
     * the Window Service.
     */
    public static String getDisplayDetails(Context context) {
        try {
            final WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            final Display display = windowManager.getDefaultDisplay();
            final DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            final StringBuilder result = new StringBuilder();
            result.append("width=").append(display.getWidth()).append('\n');
            result.append("height=").append(display.getHeight()).append('\n');
            result.append("pixelFormat=").append(display.getPixelFormat())
                    .append('\n');
            result.append("refreshRate=").append(display.getRefreshRate())
                    .append("fps").append('\n');
            result.append("metrics.density=x").append(metrics.density)
                    .append('\n');
            result.append("metrics.scaledDensity=x")
                    .append(metrics.scaledDensity).append('\n');
            result.append("metrics.widthPixels=").append(metrics.widthPixels)
                    .append('\n');
            result.append("metrics.heightPixels=").append(metrics.heightPixels)
                    .append('\n');
            result.append("metrics.xdpi=").append(metrics.xdpi).append('\n');
            result.append("metrics.ydpi=").append(metrics.ydpi);
            return result.toString();

        } catch (RuntimeException e) {
            Log.w(TAG,
                    "Couldn't retrieve DisplayDetails for : "
                            + context.getPackageName() + e.getMessage());
            return "Couldn't retrieve Display Details";
        }
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float dip2px(Context context, float dip) {
        float density = getDensity(context);
        return (float) (dip * density + 0.5);
    }

    public static float px2dip(Context context, float px) {
        float density = getDensity(context);
        return (float) (px / density + 0.5f);

    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * ５40 的分辨率上是85 （
     * <p>
     * 　当前屏幕宽度
     *
     * @return
     */
    public static int getScal(Context context) {
        return (int) (getScreenWidth(context) * 100 / 480);
    }

    /**
     * 宽全屏, 根据当前分辨率　动态获取高度 height 在８００*４８０情况下　的高度
     *
     * @return
     */
    public static int get480Height(Context context, int height480) {
        int width = getScreenWidth(context);
        return (height480 * width / 480);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
