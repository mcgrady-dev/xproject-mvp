package com.mcgrady.common_res.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_res.interf.IViewHolderRelease;

import java.util.UUID;

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




    //-------------------------------------- Drawable 相关 START ------------------------------------

    /**
     * 默认的按下后的透明度变化值
     */
    private static final float DEFAULT_ALPHA_VALUE = 0.7f;
    /**
     * 默认按下使用 20% 透明度的黑色作为遮罩
     */
    private static final float DEFAULT_DARK_ALPHA_VALUE = 0.2f;

    public static Drawable createBgDrawable(@NonNull Context context, @DrawableRes int res) {
        return createBgDrawableWithDarkMode(context, res);
    }

    public static Drawable createBgColor(Context context, @ColorInt int res) {
        return createBgColorWithDarkMode(context, res);
    }


    public static Drawable createBgDrawableWithAlphaMode(@NonNull Context context, @DrawableRes int res) {
        return createBgDrawableWithAlphaMode(context, res, DEFAULT_ALPHA_VALUE);
    }

    public static Drawable createBgDrawableWithAlphaMode(@NonNull Context context, @DrawableRes int res, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        return createBgDrawable(context, res, PressedMode.ALPHA, alpha);
    }

    public static Drawable createBgDrawableWithDarkMode(@NonNull Context context, @DrawableRes int res) {
        return createBgDrawableWithDarkMode(context, res, DEFAULT_DARK_ALPHA_VALUE);
    }

    public static Drawable createBgDrawableWithDarkMode(@NonNull Context context, @DrawableRes int res, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        return createBgDrawable(context, res, PressedMode.DARK, alpha);
    }

    public static Drawable createBgColorWithAlphaMode(@NonNull Context context, @ColorInt int res) {
        return createBgColorWithAlphaMode(context, res, DEFAULT_ALPHA_VALUE);
    }

    public static Drawable createBgColorWithAlphaMode(@NonNull Context context, @ColorInt int res, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        return createBgColor(context, res, PressedMode.ALPHA, alpha);
    }

    public static Drawable createBgColorWithDarkMode(@NonNull Context context, @ColorInt int res) {
        return createBgColor(context, res, PressedMode.DARK, DEFAULT_DARK_ALPHA_VALUE);
    }

    public static Drawable createBgColorWithDarkMode(@NonNull Context context, @ColorInt int res, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        return createBgColor(context, res, PressedMode.DARK, alpha);
    }


    /**
     * 使用一个 Drawable 资源生成一个具有按下效果的 StateListDrawable
     *
     * @param context context
     * @param res     drawable  resource
     * @param mode    mode for press
     * @param alpha   value
     * @return a stateListDrawable
     */
    private static Drawable createBgDrawable(@NonNull Context context, @DrawableRes int res, @PressedMode.Mode int mode, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        Drawable normal = context.getResources().getDrawable(res);
        Drawable pressed = context.getResources().getDrawable(res);
        Drawable unable = context.getResources().getDrawable(res);
        pressed.mutate();
        unable.mutate();
        pressed = getPressedStateDrawable(context, mode, alpha, pressed);
        unable = getUnableStateDrawable(context, unable);

        return createStateListDrawable(normal, pressed, unable);
    }

    private static Drawable createBgColor(Context context, @ColorInt int resBackgroundColor, @PressedMode.Mode int mode, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        ColorDrawable colorDrawableNormal = new ColorDrawable();
        ColorDrawable colorDrawablePressed = new ColorDrawable();
        ColorDrawable colorDrawableUnable = new ColorDrawable();

        colorDrawableNormal.setColor(resBackgroundColor);
        colorDrawablePressed.setColor(resBackgroundColor);
        colorDrawableUnable.setColor(resBackgroundColor);
        Drawable pressed = getPressedStateDrawable(context, mode, alpha, colorDrawablePressed);
        Drawable unable = getUnableStateDrawable(context, colorDrawableUnable);

        return createStateListDrawable(colorDrawableNormal, pressed, unable);
    }

    @NonNull
    private static StateListDrawable createStateListDrawable(Drawable colorDrawableNormal, Drawable colorDrawablePressed, Drawable colorDrawableUnable) {
        final StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, colorDrawablePressed);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, colorDrawableUnable);
        stateListDrawable.addState(new int[]{}, colorDrawableNormal);
        return stateListDrawable;
    }


    private static Drawable getPressedStateDrawable(Context context, @PressedMode.Mode int mode, @FloatRange(from = 0.0f, to = 1.0f) float alpha, @NonNull Drawable pressed) {
        //ColorDrawable is not supported on 4.4 because the size of the ColorDrawable can not be determined unless the View size is passed in
        if (isKitkat() && !(pressed instanceof ColorDrawable)) {
            return kitkatDrawable(context, pressed, mode, alpha);
        }
        switch (mode) {
            case PressedMode.ALPHA:
                pressed.setAlpha(convertAlphaToInt(alpha));
                break;
            case PressedMode.DARK:
                pressed.setColorFilter(alphaColor(Color.BLACK, convertAlphaToInt(alpha)), PorterDuff.Mode.SRC_ATOP);
                break;
            default:
                pressed.setAlpha(convertAlphaToInt(alpha));
        }
        return pressed;
    }

    private static Drawable kitkatDrawable(Context context, @NonNull Drawable pressed, @PressedMode.Mode int mode, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        Bitmap bitmap = Bitmap.createBitmap(pressed.getIntrinsicWidth(), pressed.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(bitmap);
        switch (mode) {
            case PressedMode.ALPHA:
                pressed.setAlpha(convertAlphaToInt(alpha));
                break;
            case PressedMode.DARK:
                pressed.setColorFilter(alphaColor(Color.BLACK, convertAlphaToInt(alpha)), PorterDuff.Mode.SRC_ATOP);
                break;
        }
        pressed.setBounds(0, 0, pressed.getIntrinsicWidth(), pressed.getIntrinsicHeight());
        pressed.draw(myCanvas);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static Drawable kitkatUnableDrawable(Context context, @NonNull Drawable pressed) {
        Bitmap bitmap = Bitmap.createBitmap(pressed.getIntrinsicWidth(), pressed.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(bitmap);
        pressed.setAlpha(convertAlphaToInt(0.5f));
        pressed.setBounds(0, 0, pressed.getIntrinsicWidth(), pressed.getIntrinsicHeight());
        pressed.draw(myCanvas);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static boolean isKitkat() {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT;
    }

    private static Drawable getUnableStateDrawable(Context context, @NonNull Drawable unable) {
        if (isKitkat() && !(unable instanceof ColorDrawable)) {
            return kitkatUnableDrawable(context, unable);
        }
        unable.setAlpha(convertAlphaToInt(0.5f));
        return unable;
    }

    private static int convertAlphaToInt(@FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        return (int) (255 * alpha);
    }

    private static int alphaColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }


    //-------------------------------------- Drawable 相关 END --------------------------------------
}
