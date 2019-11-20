package com.mcgrady.xskeleton.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.*
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.appcompat.content.res.AppCompatResources
import com.blankj.utilcode.util.LogUtils

/**
 * Created by mcgrady on 2019-08-26.
 */
object DrawableHelper {
    private val TAG = DrawableHelper::class.java.simpleName
    //节省每次创建时产生的开销，但要注意多线程操作synchronized
    private val sCanvas = Canvas()

    /**
     * 从一个view创建Bitmap。
     * 注意点：绘制之前要清掉 View 的焦点，因为焦点可能会改变一个 View 的 UI 状态。
     * 来源：https://github.com/tyrantgit/ExplosionField
     *
     * @param view  传入一个 View，会获取这个 View 的内容创建 Bitmap。
     * @param scale 缩放比例，对创建的 Bitmap 进行缩放，数值支持从 0 到 1。
     */
    @JvmOverloads
    fun createBitmapFromView(view: View, scale: Float = 1f): Bitmap? {
        if (view is ImageView) {
            val drawable = view.drawable
            if (drawable != null && drawable is BitmapDrawable) {
                return drawable.bitmap
            }
        }
        view.clearFocus()
        val bitmap = createBitmapSafely((view.width * scale).toInt(),
                (view.height * scale).toInt(), Bitmap.Config.ARGB_8888, 1)
        if (bitmap != null) {
            synchronized(sCanvas) {
                val canvas = sCanvas
                canvas.setBitmap(bitmap)
                canvas.save()
                canvas.drawColor(Color.WHITE) // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
                canvas.scale(scale, scale)
                view.draw(canvas)
                canvas.restore()
                canvas.setBitmap(null)
            }
        }
        return bitmap
    }

    /**
     * 从一个view创建Bitmap。把view的区域截掉leftCrop/topCrop/rightCrop/bottomCrop
     */
    fun createBitmapFromView(view: View, leftCrop: Int, topCrop: Int, rightCrop: Int, bottomCrop: Int): Bitmap? {
        val originBitmap = createBitmapFromView(view) ?: return null
        val cutBitmap = createBitmapSafely(view.width - rightCrop - leftCrop, view.height - topCrop - bottomCrop, Bitmap.Config.ARGB_8888, 1)
                ?: return null
        val canvas = Canvas(cutBitmap)
        val src = Rect(leftCrop, topCrop, view.width - rightCrop, view.height - bottomCrop)
        val dest = Rect(0, 0, view.width - rightCrop - leftCrop, view.height - topCrop - bottomCrop)
        canvas.drawColor(Color.WHITE) // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
        canvas.drawBitmap(originBitmap, src, dest, null)
        originBitmap.recycle()
        return cutBitmap
    }

    /**
     * 安全的创建bitmap。
     * 如果新建 Bitmap 时产生了 OOM，可以主动进行一次 GC - System.gc()，然后再次尝试创建。
     *
     * @param width      Bitmap 宽度。
     * @param height     Bitmap 高度。
     * @param config     传入一个 Bitmap.Config。
     * @param retryCount 创建 Bitmap 时产生 OOM 后，主动重试的次数。
     * @return 返回创建的 Bitmap。
     */
    fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config?, retryCount: Int): Bitmap? {
        return try {
            Bitmap.createBitmap(width, height, config)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            if (retryCount > 0) {
                System.gc()
                return createBitmapSafely(width, height, config, retryCount - 1)
            }
            null
        }
    }

    /**
     * 创建一张指定大小的纯色图片，支持圆角
     *
     * @param resources    Resources对象，用于创建BitmapDrawable
     * @param width        图片的宽度
     * @param height       图片的高度
     * @param cornerRadius 图片的圆角，不需要则传0
     * @param filledColor  图片的填充色
     * @return 指定大小的纯色图片
     */
    fun createDrawableWithSize(resources: Resources?, width: Int, height: Int, cornerRadius: Int, @ColorInt filledColor: Int): BitmapDrawable {
        var filledColor = filledColor
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        if (filledColor == 0) {
            filledColor = Color.TRANSPARENT
        }
        if (cornerRadius > 0) {
            val paint = Paint()
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            paint.color = filledColor
            canvas.drawRoundRect(RectF(0, 0, width.toFloat(), height.toFloat()), cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
        } else {
            canvas.drawColor(filledColor)
        }
        return BitmapDrawable(resources, output)
    }

    /**
     * 设置Drawable的颜色
     * **这里不对Drawable进行mutate()，会影响到所有用到这个Drawable的地方，如果要避免，请先自行mutate()**
     */
    fun setDrawableTintColor(drawable: Drawable, @ColorInt tintColor: Int): ColorFilter {
        val colorFilter = LightingColorFilter(Color.argb(255, 0, 0, 0), tintColor)
        drawable.colorFilter = colorFilter
        return colorFilter
    }

    /**
     * 由一个drawable生成bitmap
     */
    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null else if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val intrinsicWidth = drawable.intrinsicWidth
        val intrinsicHeight = drawable.intrinsicHeight
        return if (!(intrinsicWidth > 0 && intrinsicHeight > 0)) null else try {
            val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 创建一张渐变图片，支持韵脚。
     *
     * @param startColor 渐变开始色
     * @param endColor   渐变结束色
     * @param radius     圆角大小
     * @param centerX    渐变中心点 X 轴坐标
     * @param centerY    渐变中心点 Y 轴坐标
     * @return 返回所创建的渐变图片。
     */
    @TargetApi(16)
    fun createCircleGradientDrawable(@ColorInt startColor: Int,
                                     @ColorInt endColor: Int, radius: Int,
                                     @FloatRange(from = 0f, to = 1f) centerX: Float,
                                     @FloatRange(from = 0f, to = 1f) centerY: Float): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.colors = intArrayOf(
                startColor,
                endColor
        )
        gradientDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        gradientDrawable.gradientRadius = radius.toFloat()
        gradientDrawable.setGradientCenter(centerX, centerY)
        return gradientDrawable
    }

    /**
     * 动态创建带上分隔线或下分隔线的Drawable。
     *
     * @param separatorColor 分割线颜色。
     * @param bgColor        Drawable 的背景色。
     * @param top            true 则分割线为上分割线，false 则为下分割线。
     * @return 返回所创建的 Drawable。
     */
    fun createItemSeparatorBg(@ColorInt separatorColor: Int, @ColorInt bgColor: Int, separatorHeight: Int, top: Boolean): LayerDrawable {
        val separator = ShapeDrawable()
        separator.paint.style = Paint.Style.FILL
        separator.paint.color = separatorColor
        val bg = ShapeDrawable()
        bg.paint.style = Paint.Style.FILL
        bg.paint.color = bgColor
        val layers = arrayOf<Drawable>(separator, bg)
        val layerDrawable = LayerDrawable(layers)
        layerDrawable.setLayerInset(1, 0, if (top) separatorHeight else 0, 0, if (top) 0 else separatorHeight)
        return layerDrawable
    }

    /////////////// VectorDrawable /////////////////////
    fun getVectorDrawable(context: Context, @DrawableRes resVector: Int): Drawable? {
        return try {
            AppCompatResources.getDrawable(context, resVector)
        } catch (e: Exception) {
            LogUtils.d(TAG, "Error in getVectorDrawable. resVector=" + resVector + ", resName=" +
                    context.resources.getResourceName(resVector) + e.message)
            null
        }
    }

    fun vectorDrawableToBitmap(context: Context, @DrawableRes resVector: Int): Bitmap? {
        val drawable = getVectorDrawable(context, resVector)
        if (drawable != null) {
            val b = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            drawable.setBounds(0, 0, c.width, c.height)
            drawable.draw(c)
            return b
        }
        return null
    } /////////////// VectorDrawable /////////////////////
}