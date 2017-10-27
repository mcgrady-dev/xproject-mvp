package com.mcgrady.xproject.base;

import android.view.View;
import android.view.ViewGroup;

import com.mcgrady.xproject.R;

/**
 * Created by mcgrady on 2017/10/23.
 */

public abstract class RootFragment<T extends BasePresenter> extends BaseFragment<T> {

    private static final int STATE_MAIN = 0x00;
    private static final int STATE_LOADING = 0x01;
    private static final int STATE_ERROR = 0x02;

    private View errorView;
    private View loadingView;
    private ViewGroup rootView;
    private ViewGroup parentView;

    private int errorRes = R.layout.view_error;

    private int currentState = STATE_MAIN;
    private boolean isErrorViewAdded = false;


    @Override
    protected void init() {
        if (getView() == null)
            return;

        rootView = getView().findViewById(R.id.view_main);
        if (rootView == null)
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'view_main'.");
        if (!(rootView.getParent() instanceof ViewGroup))
            throw new IllegalStateException(
                    "view_main's ParentView should be a ViewGroup.");

        parentView = (ViewGroup) rootView.getParent();
        View.inflate(mContext, R.layout.view_progress, parentView);
        loadingView = parentView.findViewById(R.id.view_loading);
        loadingView.setVisibility(View.GONE);
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateError() {
        if (currentState == STATE_ERROR)
            return;

        if (!isErrorViewAdded) {
            View.inflate(mContext, errorRes, parentView);
            errorView = parentView.findViewById(R.id.view_error);
            isErrorViewAdded = true;
            if (errorView == null)
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
        }

        hideCurrentView();
        currentState = STATE_ERROR;
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateLoading() {
        if (currentState == STATE_LOADING)
            return;

        hideCurrentView();
        currentState = STATE_LOADING;
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateMain() {
        if (currentState == STATE_MAIN)
            return;

        hideCurrentView();
        currentState = STATE_MAIN;
        rootView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case STATE_MAIN:
                rootView.setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                loadingView.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                errorView.setVisibility(View.GONE);
                break;
        }
    }
}
