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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

    Observer<String> observer;
    Observable mObservable = Observable.interval(1,TimeUnit.SECONDS);
    private List<Integer> list;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    long currentTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_home;
    }

    @Override
    protected void init() {
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTableLayout.setupWithViewPager(mViewPager);

        list = new ArrayList<>();
        for (int i = 0; i < 129; i++) {
            list.add(i);
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

//        Observable.range(1, 20)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        LogUtil.d("hello " + integer);
//                    }
//                });

//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        LogUtil.d("hello " + aLong);
//                    }
//                });

//        Observable.just("hello").map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(@NonNull String s) throws Exception {
//                return s.length();
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(@NonNull Integer integer) throws Exception {
//                LogUtil.d("hello "+ integer);
//            }
//        });


//        Observable.just(list).doOnNext(new Consumer<List<Integer>>() {
//            @Override
//            public void accept(@NonNull List<Integer> integers) throws Exception {
//                LogUtil.d("所在的线程：" + Thread.currentThread().getName());
//                for (int i = 0; i < integers.size(); i++) {
//                    int integer = integers.get(i);
//                    LogUtil.d("integer " + integer + " + 1 = " + (integer + 1));
//                    integers.set(i, integer + 1);
//                }
//            }
//        }).map(new Function<List<Integer>, List<String>>() {
//            @Override
//            public List<String> apply(@NonNull List<Integer> integers) throws Exception {
//                LogUtil.d("所在的线程：" + Thread.currentThread().getName());
//                List list = new ArrayList();
//                for (Integer integer : integers) {
//                    list.add(String.valueOf(integer));
//                }
//
//                return list;
//            }
//        }).flatMap(new Function<List<String>, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(@NonNull List<String> strings) throws Exception {
//                LogUtil.d("所在的线程：" + Thread.currentThread().getName());
//                return Observable.fromIterable(strings).delay(3, TimeUnit.SECONDS);
//            }
//        }).subscribeOn(Schedulers.newThread())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                LogUtil.d("所在的线程：" + Thread.currentThread().getName());
//                LogUtil.d("hello " + s);
//            }
//        });

//        Retrofit retrofit = create();
//        TestApi api = retrofit.create(TestApi.class);
//        Observable<Activity> observable = api.getAllCity(appKey);

//        Disposable subscribe = Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(@NonNull Integer integer) throws Exception {
//
//            }
//        });


//        Flowable.just(list)
//                .flatMap(new Function<List<Integer>, Publisher<String>>() {
//                    @Override
//                    public Publisher<String> apply(@NonNull List<Integer> integers) throws Exception {
//                        return Flowable.fromIterable(integers).map(new Function<Integer, String>() {
//                            @Override
//                            public String apply(@NonNull Integer integer) throws Exception {
//                                return "hello " + integer;
//                            }
//                        });
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        LogUtil.d(s);
//                    }
//                });

//        Flowable.fromIterable(list)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(@NonNull Integer integer) throws Exception {
//                        return "hello " + integer;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        LogUtil.d(s);
//                    }
//                });

//        Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
//                for (Integer integer : list) {
//                    e.onNext("hello " + integer);
//                }
//            }
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        s.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        LogUtil.d(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        LogUtil.d(t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


//        Flowable.create(new FlowableOnSubscribe<Response>() {
//            @Override
//            public void subscribe(@NonNull FlowableEmitter<Response> e) throws Exception {
//                Request.Builder builder = new Request.Builder()
//                        .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
//                        .get();
//                Request request = builder.build();
//                Call call = new OkHttpClient().newCall(request);
//                Response response = call.execute();
//                e.onNext(response);
//            }
//        }, BackpressureStrategy.ERROR).map(new Function<Response, MobileAddress>() {
//            @Override
//            public MobileAddress apply(@NonNull Response response) throws Exception {
//                if (response.isSuccessful()) {
//                    ResponseBody body = response.body();
//                    if (body != null) {
//                        LogUtil.d("map:转换前:" + response.body());
//                        return new Gson().fromJson(body.string(), MobileAddress.class);
//                    }
//                }
//
//                return null;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MobileAddress>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//
//                    }
//
//                    @Override
//                    public void onNext(MobileAddress mobileAddress) {
//                        LogUtil.d(mobileAddress.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        LogUtil.d(t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        Observable.zip(Observable.just("1", "2", "3", "4"), Observable.just(5, 6, 7, 8),
//                new BiFunction<String, Integer, String>() {
//                    @Override
//                    public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
//                        return s + "、" + integer;
//                    }
//                }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                LogUtil.d("hello " + s);
//            }
//        });

        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LogUtil.d("hello " + integer);
                    }
                });

    }

    private void start() {
        if (observer == null) {
            observer = new Observer<String>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    LogUtil.d("start");
                }

                @Override
                public void onNext(@NonNull String s) {
                    LogUtil.d(s);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogUtil.d("error");
                }

                @Override
                public void onComplete() {
                    LogUtil.d("done");
                }
            };
        }



        // 0秒延迟，1秒后计步器+1
        Observable.interval(1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong) throws Exception {
                        currentTime += 1000;


                        return "hello " + currentTime;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void test() {
        mCompositeDisposable.add(Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong) throws Exception {
                        currentTime += 1000;
                        return "hello " + currentTime;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }

    private DisposableObserver getObserver() {
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                LogUtil.d(s);
            }

            @Override
            public void onComplete() {
                LogUtil.d("onComplete");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(e.toString());
            }
        };

        return disposableObserver;
    }

    @Override
    public void onResume() {
        super.onResume();
//        start();
//        test();
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (observer != null) {
//            observer.onComplete();
//        }

//        mCompositeDisposable.clear();
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

//    private static Retrofit create() {
//        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
//        builder.readTimeout(10, TimeUnit.SECONDS);
//        builder.connectTimeout(9, TimeUnit.SECONDS);
//
//        return new Retrofit.Builder().baseUrl()
//                .client(builder.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//    }
}
