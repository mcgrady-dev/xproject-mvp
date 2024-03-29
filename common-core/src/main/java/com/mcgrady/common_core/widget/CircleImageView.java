package com.mcgrady.common_core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * <p>Private class created to work around issues with AnimationListeners being called
 * before the animation is actually complete and support shadows on older platforms.</p>
 *
 * @author: mcgrady
 * @date: 2019/1/18
 */

public class CircleImageView extends AppCompatImageView {

    protected static final int KEY_SHADOW_COLOR = 0x1E000000;
    protected static final int FILL_SHADOW_COLOR = 0x3D000000;
    // PX
    protected static final float X_OFFSET = 0f;
    protected static final float Y_OFFSET = 1.75f;
    protected static final float SHADOW_RADIUS = 3.5f;
    protected static final int SHADOW_ELEVATION = 4;

    int mShadowRadius;

    public CircleImageView(Context context, int color) {
        super(context);
        final View thisView = this;
        final float density = thisView.getResources().getDisplayMetrics().density;
        final int shadowYOffset = (int) (density * Y_OFFSET);
        final int shadowXOffset = (int) (density * X_OFFSET);

        mShadowRadius = (int) (density * SHADOW_RADIUS);

        ShapeDrawable circle;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            circle = new ShapeDrawable(new OvalShape());

            //FIXME
//            thisView.setElevation(SHADOW_ELEVATION * density);
        } else {
            OvalShape oval = new OvalShadow(mShadowRadius);
            circle = new ShapeDrawable(oval);
            thisView.setLayerType(LAYER_TYPE_SOFTWARE, circle.getPaint());
            circle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset,
                    KEY_SHADOW_COLOR);
            final int padding = mShadowRadius;
            // set padding so the inner image sits correctly within the shadow.
            thisView.setPadding(padding, padding, padding, padding);
        }
        circle.getPaint().setColor(color);
        thisView.setBackground(circle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final View thisView = this;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Build.VERSION.SDK_INT < 21) {
            super.setMeasuredDimension(
                    thisView.getMeasuredWidth() + mShadowRadius * 2,
                    thisView.getMeasuredHeight() + mShadowRadius * 2);
        }
    }

    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint;

        OvalShadow(int shadowRadius) {
            super();
            mShadowPaint = new Paint();
            mShadowRadius = shadowRadius;
            updateRadialGradient((int) super.rect().width());
        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);
            updateRadialGradient((int) width);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            final View thisView = CircleImageView.this;
            final int viewWidth = thisView.getWidth();
            final int viewHeight = thisView.getHeight();
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2, mShadowPaint);
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2 - mShadowRadius, paint);
        }

        private void updateRadialGradient(int diameter) {
            mRadialGradient = new RadialGradient(diameter / 2, diameter / 2,
                    mShadowRadius, new int[] { FILL_SHADOW_COLOR, Color.TRANSPARENT },
                    null, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mRadialGradient);
        }
    }
}
