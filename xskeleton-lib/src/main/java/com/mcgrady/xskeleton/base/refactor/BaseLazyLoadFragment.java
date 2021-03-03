package com.mcgrady.xskeleton.base.refactor;

import com.mcgrady.xskeleton.base.BaseFragment;

/**
 * Created by mcgrady on 1/29/21.
 */
public abstract class BaseLazyLoadFragment extends BaseFragment {

    private boolean isLoaded;
    private boolean isVisibleToUser;
    private boolean isCallResume;
    private boolean isCallUserVisibleHint;

    @Override
    public void onResume() {
        super.onResume();
        isCallResume = true;
        if (!isCallUserVisibleHint) isVisibleToUser = !isHidden();
        handleLazyInit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisibleToUser = !hidden;
        handleLazyInit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        isCallUserVisibleHint = true;
        handleLazyInit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
        isVisibleToUser = false;
        isCallUserVisibleHint = false;
        isCallResume = false;
    }

    private void handleLazyInit() {
        if (!isLoaded && isVisibleToUser && isCallResume) {
            lazyInit();
            isLoaded = true;
        }
    }

    protected abstract void lazyInit();
}
