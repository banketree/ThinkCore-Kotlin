/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinkcore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @处理图片的工具类.
 */
public class TImageUtils {
    private static float[] carray = new float[20];

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public static Bitmap toGreyImg(Bitmap img) {
        int width = img.getWidth(); // 获取位图的宽
        int height = img.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        paint.setColorFilter(null);
        c.drawBitmap(bmpGrayscale, 0, 0, paint);
        ColorMatrix cm = new ColorMatrix();
        getValueBlackAndWhite();
        cm.set(carray);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static void getValueSaturation() {
        // 高饱和度
        carray[0] = 5;
        carray[1] = 0;
        carray[2] = 0;
        carray[3] = 0;
        carray[4] = -254;
        carray[5] = 0;
        carray[6] = 5;
        carray[7] = 0;
        carray[8] = 0;
        carray[9] = -254;
        carray[10] = 0;
        carray[11] = 0;
        carray[12] = 5;
        carray[13] = 0;
        carray[14] = -254;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 5;
        carray[19] = -254;

    }

    private static void getValueBlackAndWhite() {

        // 黑白
        carray[0] = (float) 0.308;
        carray[1] = (float) 0.609;
        carray[2] = (float) 0.082;
        carray[3] = 0;
        carray[4] = 0;
        carray[5] = (float) 0.308;
        carray[6] = (float) 0.609;
        carray[7] = (float) 0.082;
        carray[8] = 0;
        carray[9] = 0;
        carray[10] = (float) 0.308;
        carray[11] = (float) 0.609;
        carray[12] = (float) 0.082;
        carray[13] = 0;
        carray[14] = 0;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 1;
        carray[19] = 0;
    }

    /***/
    /**
     * 去色同时加圆角
     *
     * @param bmpOriginal 原图
     * @param pixels      圆角弧度
     * @return 修改后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayscale(bmpOriginal), pixels);
    }

    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 使圆角功能支持BitampDrawable
     *
     * @param bitmapDrawable
     * @param pixels
     * @return
     */
    public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
                                               int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 生成图片倒影
     *
     * @param originalImage
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage) {

        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.MIRROR);

        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public static boolean createBitmap(Bitmap bitmap, String filePath,
                                       int pixWidth, int pixHeight, CompressFormat format, int quality) {
        boolean result = false;
        try {
            Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, pixWidth,
                    pixHeight, true);

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(file));
            result = mBitmap.compress(format, quality, bufferedOutputStream);// format=Bitmap.CompressFormat.JPEG|quality=100
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return result;
        } catch (Exception e) {
        }

        return result;
    }

    public static Bitmap getBitmap(String filePath, int pixWidth, int pixHeight) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true; // 不会生成Bitmap对象
            BitmapFactory.decodeFile(filePath, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, pixWidth
                    * pixHeight);
            opts.inJustDecodeBounds = false;

            return BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
        }

        return null;
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // //////////////////////////////////////////////////////////////////////
    // 模糊处理
    public static Bitmap getBlur(Bitmap sentBitmap, int radius,
                                 boolean canReuseInBitmap) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /*************************************** BLUR START ****************************************/
    /**
     * 模糊处理图片，默认先缩小（默认缩放为原图的1/8），再模糊处理再放大（高效率）
     *
     * @param bm
     * @param view
     * @return
     */
    public static Bitmap getFastBlur(Bitmap bm, View view) {
        return getFastBlur(bm, view, true, 8);
    }

    /**
     * 模糊处理图片
     *
     * @param bm
     * @param view
     * @param downScale
     * @param scaleF
     * @return
     */
    public static Bitmap getFastBlur(Bitmap bm, View view, boolean downScale,
                                     float scaleF) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        if (downScale) {
            scaleFactor = scaleF;
            radius = 2;
        }

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        // 处理bitmap缩放的时候，就可以达到双缓冲的效果，模糊处理的过程就更加顺畅了。
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bm, 0, 0, paint);

        overlay = getBlur(overlay, (int) radius, true);
        return overlay;
    }

    /*************************************** 图片压缩计算 BEGIN ****************************************/
    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String getStringByBitmap(String filePath, int w, int h) {

        Bitmap bm = getSmallBitmap(filePath, w, h);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * 计算图片的缩放值
     *
     * @param o
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int getSampleSize(BitmapFactory.Options o, int reqWidth,
                                    int reqHeight) {
        // Raw height and width of image

        // if (height > reqHeight || width > reqWidth) {
        //
        // // Calculate ratios of height and width to requested height and
        // // width
        // final int heightRatio = Math.round((float) height / (float)
        // reqHeight);
        // final int widthRatio = Math.round((float) width / (float) reqWidth);
        // // final int heightRatio = height / reqHeight;
        // // final int widthRatio = width / reqWidth;
        //
        // // Choose the smallest ratio as inSampleSize value, this will
        // // guarantee
        // // a final image with both dimensions larger than or equal to the
        // // requested height and width.
        // inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        // }

        // while((h = h / 2) > reqHeight && (w = w / 2) > reqWidth){
        // inSampleSize *= 2;
        // }

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            // if (width_tmp / 2 < requiredSize || height_tmp / 2 <
            // requiredSize){
            // break;
            // }
            // width_tmp /= 2;
            // height_tmp /= 2;
            // scale *= 2;
            if (width_tmp <= reqWidth || height_tmp <= reqHeight) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;

        }

        return scale;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = getSampleSize(options, w, h);
        // options.inSampleSize = computeSampleSize(options, -1, w * h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        // Bitmap b = BitmapFactory.decodeFile(filePath, options);

        // OperatePic.zoomBitmap(b, w, h);
        return getCameraPictureOriginal(filePath,
                BitmapFactory.decodeFile(filePath, options));
    }

    public static Bitmap getSmallBitmapZoom(String filePath, int w, int h) {
        // final BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inJustDecodeBounds = true;
        // BitmapFactory.decodeFile(filePath, options);
        //
        // // Calculate inSampleSize
        // options.inSampleSize = calculateInSampleSize(options, w, h);
        // // options.inSampleSize = computeSampleSize(options, -1, w * h);
        //
        // // Decode bitmap with inSampleSize set
        // options.inJustDecodeBounds = false;
        //
        // Bitmap b = BitmapFactory.decodeFile(filePath, options);

        return getBitmapByZoom(getSmallBitmap(filePath, w, h), w, h);
    }

    public static Bitmap getSmallBitmapQuality(String filePath, int w, int h,
                                               int quality) {
        Bitmap bm = getSmallBitmap(filePath, w, h);

        if (quality >= 100 || quality <= 0) {
            return bm;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, quality, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

        return bitmap;
    }

    /**
     * 压缩到指定大小容量
     *
     * @param image
     * @param size
     * @return
     */
    public static ByteArrayInputStream getImageByCompress(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null,
        // null);//把ByteArrayInputStream数据生成图片
        return isBm;
    }

    public static void getImage2SDByCompress(File file, String srcPath,
                                             float ww, float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;//这里设置高度为800f
        // float ww = 480f;//这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        bitmap = getCameraPictureOriginal(srcPath, bitmap); // 保证图片方向正常

        ByteArrayInputStream isBm = getImageByCompress(bitmap, size);// 把压缩后的数据baos存放到ByteArrayInputStream中
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte[] buffer = new byte[isBm.available()];
            isBm.read(buffer);
            os.write(buffer, 0, buffer.length);
            os.flush();
        } catch (Exception e) {
            Log.e("getImage2SDByCompress", e.toString());
        } finally {
            try {
                if (isBm != null)
                    isBm.close();

                if (os != null)
                    os.close();
            } catch (Exception e2) {
            }

            if (bitmap != null)
                bitmap.recycle();
            bitmap = null;
        }

    }

    public static Bitmap getImageByCompressed(String srcPath, float ww,
                                              float hh, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;//这里设置高度为800f
        // float ww = 480f;//这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return BitmapFactory.decodeStream(getImageByCompress(bitmap, size), // 缩小到指定容量
                null, null);// 把ByteArrayInputStream数据生成图片
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /*************************************** 图片压缩计算 END ****************************************/

    /*************************************** 圆角/投影羽化/倒影 BEGIN ****************************************/
    /**
     * 对图片进行圆角处理
     *
     * @param bitmap  要处理的Bitmap对象
     * @param roundPx 圆角半径设置
     * @return Bitmap对象
     * @author com.tiantian
     */
    public static Bitmap getBitmapByRoundedCorner(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 图片加投影 羽化效果
     *
     * @param originalBitmap
     * @return
     */
    public static Bitmap getBitmapShadow(Bitmap originalBitmap) {
        // Bitmap originalBitmap = drawableToBitmap(drawable);
        BlurMaskFilter blurFilter = new BlurMaskFilter(10,
                BlurMaskFilter.Blur.OUTER);
        Paint shadowPaint = new Paint();
        shadowPaint.setMaskFilter(blurFilter);

		/*
         * int[] offsetXY = new int[2]; Bitmap shadowImage =
		 * originalBitmap.extractAlpha(shadowPaint, offsetXY);
		 * 
		 * Bitmap bmp=shadowImage.copy(Config.ARGB_8888, true); Canvas c = new
		 * Canvas(bmp); c.drawBitmap(originalBitmap, -offsetXY[0], -offsetXY[1],
		 * null); return shadowImage;
		 */
        final int w = originalBitmap.getWidth();
        final int h = originalBitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w + 20, h + 20,
                Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawBitmap(originalBitmap, 10, 10, shadowPaint);
        c.drawBitmap(originalBitmap, 10, 10, null);
        return bmp;
    }

    /**
     * 对图片进行倒影处理
     *
     * @param bitmap
     * @return
     * @author com.tiantian
     */
    public static Bitmap getImageByOrigin(Bitmap bitmap) {// reflection
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /*************************************** 圆角/投影羽化/倒影 END ****************************************/

    /*********************************** 图片基本处理（缩放/转换）BEGIN *************************************/
    /**
     * 放缩图片处理
     *
     * @param bitmap 要放缩的Bitmap对象
     * @param width  放缩后的宽度
     * @param height 放缩后的高度
     * @return 放缩后的Bitmap对象
     * @author com.tiantian
     */
    public static Bitmap getBitmapByZoom(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 放缩图片处理
     *
     * @param bitmap      要放缩的Bitmap对象
     * @param widthScale  放缩率
     * @param heightScale 放缩率
     * @return 放缩后的Bitmap对象
     * @author com.tiantian
     */
    public static Bitmap getBitmapByZoomScale(Bitmap bitmap, float widthScale,
                                              float heightScale) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = widthScale;
        float scaleHeight = heightScale;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * Drawable缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable getDrawableByZoom(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = getDrawableByBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * 将Bitmap转化为Drawable
     *
     * @param bitmap
     * @return
     * @author com.tiantian
     */
    public static Drawable getBitmapByDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     * @author com.tiantian
     */
    public static Bitmap getDrawableByBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /*********************************** 图片基本处理（缩放/转换）BEGIN *************************************/

    /*********************************** 图片基本基本信息 BEGIN *************************************/

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int getPictureDegreeFromExif(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
//            Log.e("getPictureDegreeFromExif", e.toString());
        }
        return degree;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap getImageByRotaing(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null)
            bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 处理相机照片旋转角度
     *
     * @param path 用于获取原图的信息
     * @return 原图的bitmap（可以是被压缩过的）
     */
    public static Bitmap getCameraPictureOriginal(String path, Bitmap bitmap) {
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = getPictureDegreeFromExif(path);
        if (0 == degree) {
            return bitmap;
        }
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = getImageByRotaing(degree, bitmap);
        if (bitmap != null)
            bitmap.recycle();
        return newbitmap;
    }

    /**
     * 处理相机照片旋转角度
     *
     * @param path
     * @return
     */
    public static Bitmap getCameraPicture(String path) {
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree = getPictureDegreeFromExif(path);
        BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
        opts.inSampleSize = 2;
        Bitmap cbitmap = BitmapFactory.decodeFile(path, opts);
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = getImageByRotaing(degree, cbitmap);
        return newbitmap;
    }

    public static Bitmap getBitmapByResources(Context context, int resId) {
        return BitmapFactory.decodeResource(context
                .getResources(), resId);
    }

    /*********************************** 图片基本基本信息 END *************************************/

    /*********************************** 图片初始化 BEGIN *************************************/
    /**
     * 从Resource中获取Drawable，并初始化bound
     *
     * @param context
     * @param drawableResId
     * @param bound
     * @return
     */
    public static Drawable getDrawableByResourceBounded(Context context,
                                                        int drawableResId, int bound) {
        Drawable drawable = null;
        try {
            drawable = context.getResources().getDrawable(drawableResId);
            drawable.setBounds(0, 0, bound, bound);
        } catch (Exception ex) {
//            Log.e("getResourceDrawableBounded", ex.toString());
        }
        return drawable;
    }

    // ///////////////////////////////////////////////////////////////////////////////

    /**
     * 生成圆角的背景
     *
     * @param color
     * @return
     */
    public static ShapeDrawable getShapeDrawableByCorner(int color, int corner) {
        return getShapeDrawableByCorner(color, corner, corner, corner, corner);
    }

    public static ShapeDrawable getShapeDrawableByCorner(int color,
                                                         int topLeftCorner, int topRightCorner, int bottomRightCorner,
                                                         int bottomLeftCorner) {
        Shape shape = new RoundRectShape(new float[]{topLeftCorner,
                topLeftCorner, topRightCorner, topRightCorner,
                bottomRightCorner, bottomRightCorner, bottomLeftCorner,
                bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.FILL);
        return sd;
    }

    public static ShapeDrawable getStrokeDrawableByCorner(int color,
                                                          float width, int corner) {
        return getStrokeDrawableByCorner(color, width, corner, corner, corner,
                corner);
    }

    public static ShapeDrawable getStrokeDrawableByCorner(int color,
                                                          float width, int topLeftCorner, int topRightCorner,
                                                          int bottomRightCorner, int bottomLeftCorner) {
        Shape shape = new RoundRectShape(new float[]{topLeftCorner,
                topLeftCorner, topRightCorner, topRightCorner,
                bottomRightCorner, bottomRightCorner, bottomLeftCorner,
                bottomLeftCorner}, null, null);
        ShapeDrawable sd = new ShapeDrawable(shape);
        sd.getPaint().setColor(color);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        sd.getPaint().setAntiAlias(true);
        sd.getPaint().setStrokeWidth(width);
        return sd;
    }

    public static StateListDrawable getSelectorByClickSimple(Drawable normal,
                                                             Drawable pressed) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled}, pressed);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    public static StateListDrawable getSelectorByClickColorCornerSimple(
            int normalColor, int pressedColor, int corner) {
        return getSelectorByClickSimple(
                getShapeDrawableByCorner(normalColor, corner),
                getShapeDrawableByCorner(pressedColor, corner));
    }

    public static boolean saveBitmap(Bitmap bitmap, String filePath,
                                     int PixWidth, int PixHeight) throws Exception {
        Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, PixWidth, PixHeight,
                true);
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file));
        mBitmap.compress(CompressFormat.JPEG, 100, bufferedOutputStream);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        return true;
    }

    public static Bitmap convertViewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getLayoutParams().width,
                view.getLayoutParams().height, Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(),
                view.getBottom());
        view.draw(c);

        return bitmap;
    }
}