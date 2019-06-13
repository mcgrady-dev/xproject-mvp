# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5

-dontskipnonpubliclibraryclassmembers
-verbos
-printmapping priguardMapping.txt

-optimizations !code/simplification/artithmetic,!field/*,!class/merging/*

#混淆时不生成大小写混合的类名
-dontusemixedcaseclassnames
#不忽略非公共的类库
-dontskipnonpubliclibraryclasses
#混淆过程中打印详细信息
-verbose
#关闭优化
-dontoptimize
#不预校验
-dontpreverify

-dontwarn javax.naming.**

-keepattributes Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod


#保持枚举类型values()、以及valueOf(java.lang.String)成员不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持实现Parcelable接口的类里面的Creator成员不被混淆
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

#保持实现Serializable接口的类不被混淆
-keepnames class * implements java.io.Serializable

#保持R类静态成员不被混淆
-keepclassmembers class **.R$* {
    public static <fields>;
}

#保持使用了Keep注解的方法以及类不被混淆
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

#保持使用了Keep注解的成员域以及类不被混淆
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}


################ common ###############
-keep public class * implements com.yongyou.xskeleton.integration.ConfigModule

 #实体类不参与混淆
-keep class com.yongoyu.common_core.widget.** { *; } #自定义控件不参与混淆

-keep class **.R$* {*;}
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keepclassmembers enum * {  # 使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


################ support ###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
#不警告support包中不使用的引用
-dontwarn android.support.**


################ alipay ###############
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


################ retrofit ###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }


################ butterknife ###############
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
   @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
 @butterknife.* <methods>;
}


################ gson ###############
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.sunloto.shandong.bean.** { *; }


################ glide ###############
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

################ okhttp ###############
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**


################ EventBus ###############
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep class org.greenrobot.eventbus.EventBus { *; }
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

################ autolayout ###############
-keep class com.zhy.autolayout.** { *; }
-keep interface com.zhy.autolayout.** { *; }


################ RxJava & RxAndroid ###############
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-keep class io.reactivex.** { *; }
-keep interface io.reactivex.** { *; }

-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn io.reactivex.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class io.reactivex.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn io.reactivex.internal.util.unsafe.**


################ RxLifeCycle #################
-keep class com.trello.rxlifecycle2.** { *; }
-keep interface com.trello.rxlifecycle2.** { *; }


################ RxPermissions #################
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }

################ RxCache #################
-dontwarn io.rx_cache2.internal.**
-keep class io.rx_cache2.internal.Record { *; }
-keep class io.rx_cache2.Source { *; }

-keep class io.victoralbertos.jolyglot.** { *; }
-keep interface io.victoralbertos.jolyglot.** { *; }

################ RxErrorHandler #################
 -keep class com.yongyou.xskeleton.http.handler.rxerrorhandler.** { *; }
 -keep interface com.yongyou.xskeleton.http.handler..rxerrorhandler.** { *; }


################ Canary #################
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

################ tencent ###############
# 腾讯xlog
-keep class com.tencent.mars.**{*;}
# Bugly混淆规则
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }
# wechat sdk
-keep class com.tencent.mm.opensdk.** {*;}

-keep class com.tencent.wxop.** {*;}

-keep class com.tencent.mm.sdk.** {*;}


################ ImmersionBar ###############
-keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**


################ BRVAH ###############
-keep class com.chad.library.adapter.** {*;}

-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


################ Dialogv3 ###############
-keep class com.kongzue.dialog.** { *; }
-dontwarn com.kongzue.dialog.**


################ JPush ###############
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }