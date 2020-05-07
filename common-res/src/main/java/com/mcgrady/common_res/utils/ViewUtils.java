package com.mcgrady.common_res.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_res.interf.IViewHolderRelease;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Completable;

import static com.mcgrady.xskeleton.integration.Platform.DEPENDENCY_SUPPORT_DESIGN;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/15
 */

public class ViewUtils {

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 遍历所有{@link RecyclerView.ViewHolder},释放他们需要释放的资源
     *
     * @param recyclerView
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) { return; }

        if (recyclerView.getAdapter() instanceof IViewHolderRelease) {
            for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
                final View view = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof BaseViewHolder) {
                    ((IViewHolderRelease) recyclerView.getAdapter()).onRelease(viewHolder);
                }
            }
        }
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }

    /**
     * 获取屏幕可用宽度
     * @param activity
     * @return
     */
    public static int getScreenWidthWithoutVirtualBar(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕可用高度
     * @param activity
     * @return
     */
    public static int getScreenHeightWithoutVirtualBar(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 使用 {@link Snackbar} 显示文本消息
     * @param message
     * @param isLong
     */
    public static void showSnackbar(String message, boolean isLong) {
        if (ActivityUtils.getTopActivity() == null) {
            LogUtils.w("top activity == null when showSnackbar");
            return;
        }

        Completable.fromAction(() -> {
            if (DEPENDENCY_SUPPORT_DESIGN) {
                Activity activity = ActivityUtils.getTopActivity();
                View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
            } else {
                ToastUtils.show(message);
            }
        });
    }

    public static void setTextColorPart(Context context, TextView textView, String flagStart, String part) {
        setTextColorPart(context, textView, flagStart, "", part);
    }

    public static void setTextColorPart(Context context, TextView textView, String flagStart, String flagEnd, String part) {
        setTextColorPart(context, textView, flagStart, flagEnd, part, context.getResources().getColor(android.R.color.black));
    }

    public static void setTextColorPart(Context context, TextView textView, String flagStart, String flagEnd, String part, int resId) {
        if (StringUtils.isEmpty(flagStart)) {
            flagStart = "";
        }
        if (StringUtils.isEmpty(flagEnd)) {
            flagEnd = "";
        }
        if (StringUtils.isEmpty(part)) {
            part = "";
        }
        String content = flagStart + part + flagEnd;

        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(resId);
        builder.setSpan(redSpan, flagStart.length(), (flagStart + part).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(builder);
    }

    public static void isShowBottomLine(TextView textView, boolean isShow) {
        if (isShow) {
            textView.setText(Html.fromHtml("<u>"+textView.getText()+"</u>"));
        } else {
            textView.setText(textView.getText());
        }

    }

    /**
     * 给特定字加特定颜色
     * @param color 关键字颜色
     * @param text  整体文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword){
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /**
     * 设置下划线
     */

    public static void setTvUnderLine(TextView textView) {
        //下划线
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textView.getPaint().setAntiAlias(true);
    }

    /**
     * 取消下划线
     */
    public static void clearUnderLine(TextView textView){
        textView.getPaint().setFlags(0);
        //抗锯齿
        textView.getPaint().setAntiAlias(true);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }
}
