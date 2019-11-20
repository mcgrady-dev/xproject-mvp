package com.mcgrady.xskeleton.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.StringUtils
import java.util.regex.Pattern

/**
 * Created by mcgrady on 2019-08-19.
 */
object ViewUtils {
    fun setTextColorPart(context: Context, textView: TextView, flagStart: String, part: String) {
        setTextColorPart(context, textView, flagStart, "", part)
    }

    fun setTextColorPart(context: Context, textView: TextView, flagStart: String, flagEnd: String, part: String) {
        setTextColorPart(context, textView, flagStart, flagEnd, part, context.resources.getColor(R.color.black))
    }

    fun setTextColorPart(context: Context?, textView: TextView, flagStart: String, flagEnd: String, part: String, resId: Int) {
        var flagStart = flagStart
        var flagEnd = flagEnd
        var part = part
        if (StringUtils.isEmpty(flagStart)) {
            flagStart = ""
        }
        if (StringUtils.isEmpty(flagEnd)) {
            flagEnd = ""
        }
        if (StringUtils.isEmpty(part)) {
            part = ""
        }
        val content = flagStart + part + flagEnd
        val builder = SpannableStringBuilder(content)
        val redSpan = ForegroundColorSpan(resId)
        builder.setSpan(redSpan, flagStart.length, (flagStart + part).length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        textView.text = builder
    }

    fun setDrawableLft(context: Context, textView: TextView, resId: Int) {
        textView.setCompoundDrawables(getDrawable(context, resId), null, null, null)
    }

    fun setDrawableRight(context: Context, textView: TextView, resId: Int) {
        textView.setCompoundDrawables(null, null, getDrawable(context, resId), null)
    }

    fun setDrawableTop(context: Context, textView: TextView, resId: Int) {
        textView.setCompoundDrawables(null, getDrawable(context, resId), null, null)
    }

    fun setDrawableBottom(context: Context, textView: TextView, resId: Int) {
        textView.setCompoundDrawables(null, null, null, getDrawable(context, resId))
    }

    fun resetImage(textView: TextView) {
        textView.setCompoundDrawables(null, null, null, null)
    }

    private fun getDrawable(context: Context, resId: Int): Drawable? {
        if (resId == -1) {
            return null
        }
        val drawable = context.resources.getDrawable(resId)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        return drawable
    }

    fun isShowBottomLine(textView: TextView, isShow: Boolean) {
        if (isShow) {
            textView.text = Html.fromHtml("<u>" + textView.text + "</u>")
        } else {
            textView.text = textView.text
        }
    }

    /**
     * 给特定字加特定颜色
     * @param color 关键字颜色
     * @param text  整体文本
     * @param keyword 关键字
     * @return
     */
    fun matcherSearchText(color: Int, text: String, keyword: String): SpannableString {
        val string = text.toLowerCase()
        val key = keyword.toLowerCase()
        val pattern = Pattern.compile(key)
        val matcher = pattern.matcher(string)
        val ss = SpannableString(text)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            ss.setSpan(ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ss
    }

    /**
     * 设置下划线
     */
    fun setTvUnderLine(textView: TextView) { //下划线
        textView.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        //抗锯齿
        textView.paint.isAntiAlias = true
    }

    /**
     * 取消下划线
     */
    fun clearUnderLine(textView: TextView) {
        textView.paint.flags = 0
        //抗锯齿
        textView.paint.isAntiAlias = true
    }

    /**
     * 获得资源
     */
    fun getResources(context: Context): Resources {
        return context.resources
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
    </T> */
    fun <T : View?> findViewByName(context: Context, view: View, viewName: String?): T {
        val id = getResources(context).getIdentifier(viewName, "id", context.packageName)
        return view.findViewById<View>(id) as T
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
    </T> */
    @kotlin.jvm.JvmStatic
    fun <T : View?> findViewByName(context: Context, activity: Activity, viewName: String?): T {
        val id = getResources(context).getIdentifier(viewName, "id", context.packageName)
        return activity.findViewById<View>(id) as T
    }
}