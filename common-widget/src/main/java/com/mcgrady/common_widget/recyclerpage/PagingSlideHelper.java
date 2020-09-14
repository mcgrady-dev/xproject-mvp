package com.mcgrady.common_widget.recyclerpage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 分页滑动辅助工具
 *
 * Created by mcgrady on 2019-06-14.
 */
public class PagingSlideHelper {

    private final String TAG = PagingSlideHelper.class.getSimpleName();

    enum ORIENTATION {
        HORIZONTAL, VERTICAL, NULL
    }

    private RecyclerView recyclerView = null;

    private boolean isFirstTouch = true;

    private int offsetY = 0;
    private int offsetX = 0;

    private int startY = 0;
    private int startX = 0;

    private onPageChangeListener onPageChangeListener;
    private OnScrollListenerProxy onScrollListener = new OnScrollListenerProxy();
    private OnTouchListenerProxy onTouchListener = new OnTouchListenerProxy();
    private OnFlingListenerProxy onFlingListener = new OnFlingListenerProxy();

    private ORIENTATION mOrientation = ORIENTATION.HORIZONTAL;

    private ValueAnimator animator = null;

    public void setRecyclerView(@NonNull RecyclerView recycleView) {
        recyclerView = recycleView;
        //处理滑动
        recyclerView.setOnFlingListener(onFlingListener);
        //设置滚动监听，记录滚动的状态，和总的偏移量
        recyclerView.addOnScrollListener(onScrollListener);
        //记录滚动开始的位置
        recyclerView.setOnTouchListener(onTouchListener);
        //获取滚动的方向
        updateLayoutManger();

    }

    public void updateLayoutManger() {
        RecyclerView.LayoutManager layoutManager = recyclerView == null ? null : recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return;
        }

        if (layoutManager.canScrollVertically()) {
            mOrientation = ORIENTATION.VERTICAL;
        } else if (layoutManager.canScrollHorizontally()) {
            mOrientation = ORIENTATION.HORIZONTAL;
        } else {
            mOrientation = ORIENTATION.NULL;
        }

        if (animator != null) {
            animator.cancel();
        }

        startX = 0;
        startY = 0;
        offsetX = 0;
        offsetY = 0;
    }

    /**
     * 获取总共的页数
     */
    public int getPageCount() {
        if (recyclerView != null) {
            if (mOrientation == ORIENTATION.NULL) {
                return 0;
            }

            if (mOrientation == ORIENTATION.VERTICAL && recyclerView.computeVerticalScrollExtent() != 0) {
                return recyclerView.computeVerticalScrollRange() / recyclerView.computeVerticalScrollExtent();
            } else if (recyclerView.computeHorizontalScrollExtent() != 0) {
                Log.i(TAG, "rang=" + recyclerView.computeHorizontalScrollRange() + " extent=" + recyclerView.computeHorizontalScrollExtent());
                return recyclerView.computeHorizontalScrollRange() / recyclerView.computeHorizontalScrollExtent();
            }
        }

        return 0;
    }

    public void scrollToPosition(int position) {
        if (animator == null) {
            onFlingListener.onFling(0, 0);
        }
        if (animator != null) {
            int startPoint = mOrientation == ORIENTATION.VERTICAL ? offsetY : offsetX, endPoint = 0;
            if (mOrientation == ORIENTATION.VERTICAL) {
                endPoint = recyclerView.getHeight() * position;
            } else {
                endPoint = recyclerView.getWidth() * position;
            }
            if (startPoint != endPoint) {
                animator.setIntValues(startPoint, endPoint);
                animator.start();
            }
        }
    }

    private int getPageIndex() {
        int p = 0;

        if (recyclerView != null) {
            if (recyclerView.getHeight() == 0 || recyclerView.getWidth() == 0) {
                return p;
            }
            if (mOrientation == ORIENTATION.VERTICAL) {
                p = offsetY / recyclerView.getHeight();
            } else {
                p = offsetX / recyclerView.getWidth();
            }
        }

        return p;
    }

    private int getStartPageIndex() {
        int p = 0;
        if (recyclerView != null) {
            if (recyclerView.getHeight() == 0 || recyclerView.getWidth() == 0) {
                //没有宽高无法处理
                return p;
            }
            if (mOrientation == ORIENTATION.VERTICAL) {
                p = startY / recyclerView.getHeight();
            } else {
                p = startX / recyclerView.getWidth();
            }
        }
        return p;
    }

    public void setOnPageChangeListener(onPageChangeListener listener) {
        onPageChangeListener = listener;
    }

    public class OnFlingListenerProxy extends RecyclerView.OnFlingListener {

        @Override
        public boolean onFling(int velocityX, int velocityY) {
            if (mOrientation == ORIENTATION.NULL) {
                return false;
            }
            //获取开始滚动时所在页面的index
            int p = getStartPageIndex();

            //记录滚动开始和结束的位置
            int endPoint = 0;
            int startPoint = 0;

            //如果是垂直方向
            if (mOrientation == ORIENTATION.VERTICAL) {
                startPoint = offsetY;

                if (velocityY < 0) {
                    p--;
                } else if (velocityY > 0) {
                    p++;
                }
                //更具不同的速度判断需要滚动的方向
                //注意，此处有一个技巧，就是当速度为0的时候就滚动会开始的页面，即实现页面复位
                endPoint = p * recyclerView.getHeight();

            } else {
                startPoint = offsetX;
                if (velocityX < 0) {
                    p--;
                } else if (velocityX > 0) {
                    p++;
                }
                endPoint = p * recyclerView.getWidth();

            }
            if (endPoint < 0) {
                endPoint = 0;
            }

            //使用动画处理滚动
            if (animator == null) {
                animator = new ValueAnimator().ofInt(startPoint, endPoint);

                animator.setDuration(300);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int nowPoint = (int) animation.getAnimatedValue();

                        if (mOrientation == ORIENTATION.VERTICAL) {
                            int dy = nowPoint - offsetY;
                            //这里通过RecyclerView的scrollBy方法实现滚动。
                            recyclerView.scrollBy(0, dy);
                        } else {
                            int dx = nowPoint - offsetX;
                            recyclerView.scrollBy(dx, 0);
                        }
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //回调监听
                        if (null != onPageChangeListener) {
                            onPageChangeListener.onPageChange(getPageIndex());
                        }
                        //修复双击item bug
                        recyclerView.stopScroll();
                        startY = offsetY;
                        startX = offsetX;
                    }
                });
            } else {
                animator.cancel();
                animator.setIntValues(startPoint, endPoint);
            }

            animator.start();

            return true;
        }
    }

    public class OnScrollListenerProxy extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //newState==0表示滚动停止，此时需要处理回滚
            if (newState == 0 && mOrientation != ORIENTATION.NULL) {
                boolean move;
                int vX = 0, vY = 0;
                if (mOrientation == ORIENTATION.VERTICAL) {
                    int absY = Math.abs(offsetY - startY);
                    //如果滑动的距离超过屏幕的一半表示需要滑动到下一页
                    move = absY > recyclerView.getHeight() / 2;
                    vY = 0;

                    if (move) {
                        vY = offsetY - startY < 0 ? -1000 : 1000;
                    }

                } else {
                    int absX = Math.abs(offsetX - startX);
                    move = absX > recyclerView.getWidth() / 2;
                    if (move) {
                        vX = offsetX - startX < 0 ? -1000 : 1000;
                    }

                }

                onFlingListener.onFling(vX, vY);

            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //滚动结束记录滚动的偏移量
            offsetY += dy;
            offsetX += dx;
        }
    }

    public class OnTouchListenerProxy implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //手指按下的时候记录开始滚动的坐标
            if (isFirstTouch) {
                //第一次touch可能是ACTION_MOVE或ACTION_DOWN,所以使用这种方式判断
                isFirstTouch = false;
                startY = offsetY;
                startX = offsetX;
            }
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                isFirstTouch = true;
            }

            return false;
        }

    }

    public interface onPageChangeListener {
        void onPageChange(int index);
    }
}
