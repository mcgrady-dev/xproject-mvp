package com.mcgrady.xproject.presenter;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.component.RxBus;
import com.mcgrady.xproject.contract.GoldHomeContract;
import com.mcgrady.xproject.model.DataManager;
import com.mcgrady.xproject.model.bean.GoldManagerBean;
import com.mcgrady.xproject.model.bean.GoldManagerItemBean;
import com.mcgrady.xproject.util.RxUtil;
import com.mcgrady.xproject.widget.CommonSubscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/8/30.
 */

public class GoldHomePresenter extends RxPresenter<GoldHomeContract.View> implements GoldHomeContract.Presenter {

    private DataManager mDataManager;
    private GoldManagerBean managerBean;
    private List<GoldManagerItemBean> mList;

    @Inject
    public GoldHomePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(GoldHomeContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(GoldManagerBean.class)
        .compose(RxUtil.<GoldManagerBean>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<GoldManagerBean>(mView, "设置失败", false) {
            @Override
            public void onNext(GoldManagerBean goldManagerBean) {
                managerBean = goldManagerBean;
                mView.updateTab(goldManagerBean.getManagerList());
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                registerEvent();
            }
        }));

    }


    @Override
    public void initManagerList() {
        initList();
//        mDataManager.updateGoldManagerList(new GoldManagerBean(mList));
        mView.updateTab(mList);
    }

    @Override
    public void setManagerList() {
//        mView.jumpToManager(mDataManager.getGoldManagerList());
        mView.jumpToManager(managerBean);
    }

    private void initList() {
        mList = new ArrayList<>();
        mList.add(new GoldManagerItemBean(0, true));
        mList.add(new GoldManagerItemBean(1, true));
        mList.add(new GoldManagerItemBean(2, true));
        mList.add(new GoldManagerItemBean(3, true));
        mList.add(new GoldManagerItemBean(4, false));
        mList.add(new GoldManagerItemBean(5, false));
        mList.add(new GoldManagerItemBean(6, false));
        mList.add(new GoldManagerItemBean(7, false));

        if (managerBean == null)
            managerBean = new GoldManagerBean(mList);
        else
            managerBean.setManagerList(mList);
    }
}
