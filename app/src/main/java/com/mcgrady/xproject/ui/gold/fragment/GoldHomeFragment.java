package com.mcgrady.xproject.ui.gold.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.base.BaseFragment;
import com.mcgrady.xproject.contract.GoldHomeContract;
import com.mcgrady.xproject.presenter.GoldHomePresenter;
import com.mcgrady.xproject.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by mcgrady on 2017/8/30.
 * 稀土掘金主页
 */

public class GoldHomeFragment extends BaseFragment<GoldHomePresenter> implements GoldHomeContract.View {

    @BindView(R.id.tab_gold_main)
    TabLayout mTableLayout;
    @BindView(R.id.vp_gold_main)
    ViewPager mViewPager;


    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_home;
    }

    @Override
    protected void init() {
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTableLayout.setupWithViewPager(mViewPager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("hello +" + i);
        }

        // 遍历
//        Observable<String> observable = Observable.fromIterable(list);

        // 每次订阅都会创建一个新的Observable，并且如果没有被订阅，就不会产生新的Observable
//        SomeType instance = new SomeType();
//        Observable<String> observable = instance.valueObservable();
//        instance.setValue("hello");
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull String s) {
//                LogUtil.d(s);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        observable.subscribe(observer);

        // 定时发送
//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(15)
//                .window(10, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Observable<Long>>() {
//                    @Override
//                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {
//                        LogUtil.d("doing...");
//
//                        longObservable.subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Consumer<Long>() {
//                                    @Override
//                                    public void accept(@NonNull Long aLong) throws Exception {
//                                        LogUtil.d("hello " + aLong);
//                                    }
//                                });
//                    }
//                });

        Observable.range(1, 20)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtil.d("hello " + integer);
                    }
                });


    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    class SomeType {
        private String value = "";

        public void setValue(String value) {
            this.value = value;
        }

        public Observable<String> valueObservable() {
//            return Observable.just(value);

//            return Observable.create(new ObservableOnSubscribe<String>() {
//                @Override
//                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                    e.onNext(value);
//                }
//            });

            return Observable.defer(new Callable<ObservableSource<? extends String>>() {
                @Override
                public ObservableSource<? extends String> call() throws Exception {
                    return Observable.just(value);
                }
            });
        }
    }


}
