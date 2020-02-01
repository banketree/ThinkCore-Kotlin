package me.jessyan.autosize.bean


import android.os.Parcel
import android.os.Parcelable
import android.util.DisplayMetrics

/**
 * [DisplayMetrics] 封装类
 */
class DisplayMetricsInfo : Parcelable {
    var density: Float = 0.toFloat()
    var densityDpi: Int = 0
    var scaledDensity: Float = 0.toFloat()
    var xdpi: Float = 0.toFloat()
    var screenWidthDp: Int = 0
    var screenHeightDp: Int = 0

    constructor(density: Float, densityDpi: Int, scaledDensity: Float, xdpi: Float) {
        this.density = density
        this.densityDpi = densityDpi
        this.scaledDensity = scaledDensity
        this.xdpi = xdpi
    }

    constructor(
        density: Float,
        densityDpi: Int,
        scaledDensity: Float,
        xdpi: Float,
        screenWidthDp: Int,
        screenHeightDp: Int
    ) {
        this.density = density
        this.densityDpi = densityDpi
        this.scaledDensity = scaledDensity
        this.xdpi = xdpi
        this.screenWidthDp = screenWidthDp
        this.screenHeightDp = screenHeightDp
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(this.density)
        dest.writeInt(this.densityDpi)
        dest.writeFloat(this.scaledDensity)
        dest.writeFloat(this.xdpi)
        dest.writeInt(this.screenWidthDp)
        dest.writeInt(this.screenHeightDp)
    }

    protected constructor(`in`: Parcel) {
        this.density = `in`.readFloat()
        this.densityDpi = `in`.readInt()
        this.scaledDensity = `in`.readFloat()
        this.xdpi = `in`.readFloat()
        this.screenWidthDp = `in`.readInt()
        this.screenHeightDp = `in`.readInt()
    }

    override fun toString(): String {
        return "DisplayMetricsInfo{" +
                "density=" + density +
                ", densityDpi=" + densityDpi +
                ", scaledDensity=" + scaledDensity +
                ", xdpi=" + xdpi +
                ", screenWidthDp=" + screenWidthDp +
                ", screenHeightDp=" + screenHeightDp +
                '}'.toString()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<DisplayMetricsInfo> = object : Parcelable.Creator<DisplayMetricsInfo> {
            override fun createFromParcel(source: Parcel): DisplayMetricsInfo {
                return DisplayMetricsInfo(source)
            }

            override fun newArray(size: Int): Array<DisplayMetricsInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
