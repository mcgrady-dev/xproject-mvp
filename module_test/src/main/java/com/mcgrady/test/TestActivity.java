package com.mcgrady.test;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_core.widget.recyclerpage.PagingSlideHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TestActivity extends AppCompatActivity {

    public static final String TAG = TestActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private PagingSlideHelper pagingSlideHelper;

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        setContentView(R.layout.test_activity_sliding_icontab);

//        recyclerView = findViewById(test_vp_tab);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
//        List<String> list = new ArrayList<>();
//        list.add("sss");
//        list.add("eee");
//        list.add("ddd");
//        list.add("qqq");
//        list.add("ttt");
//        list.add("uuu");
//        recyclerView.setAdapter(new TabAdapter(R.layout.item_menu_tab, list));
//        pagingSlideHelper = new PagingSlideHelper();
//        pagingSlideHelper.setRecyclerView(recyclerView);
//        pagingSlideHelper.setOnPageChangeListener(new PagingSlideHelper.onPageChangeListener() {
//            @Override
//            public void onPageChange(int index) {
//
//                LogUtils.d(index);
//            }
//        });
//        recyclerView.setLayoutManager(new HorizontalPageLayoutManager(1, 3));
//        pagingSlideHelper.updateLayoutManger();
//        pagingSlideHelper.scrollToPosition(0);
//        recyclerView.setHorizontalScrollBarEnabled(true);

        TextView tvLabel = findViewById(R.id.test_tv_label);

        SpanUtils.with(tvLabel)
                .appendLine("阿斯顿发送到发送到发的说法")
                .appendImage(R.mipmap.public_ic_time_gray, SpanUtils.ALIGN_TOP)
                .appendLine("阿斯顿发送到发送到发的说法阿斯顿发送到发送到发的说法阿斯顿发送到发送到发的说法阿斯顿发送到发送到发的说法")
                .create();


        tvTime = findViewById(R.id.test_tv_time);


        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tvTime.setText(getUTCstring());
            }
        });

        tvTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTime.performClick();
            }
        }, 300);
//        tvTime.callOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class TabAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TabAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private String getCurrentTime() {
        return TimeUtils.millis2String(System.currentTimeMillis());
    }

    public static String getUTCstring(Location location) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(location.getTime()));
        // Append the string "UTC" to the date
        if(!date.contains("UTC")) {
            date += " UTC";
        }
        return date;
    }

    private String getCurTime() {
        Date date = new Date();
        date.setTime(1573297200000l);

        //获得系统的时间，单位为毫秒,转换为妙
        long totalMilliSeconds = System.currentTimeMillis();

//        totalMilliSeconds = date.getTime() - totalMilliSeconds;

//        DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);//格式化输出
//        TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Guangdong");//获取时区 这句加上，很关键。
//        dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区
        long totalSeconds = totalMilliSeconds / 1000;

        //求出现在的秒
        long currentSecond = totalSeconds % 60;

        //求出现在的分
        long totalMinutes = totalSeconds / 60;
        long currentMinute = totalMinutes % 60;

        //求出现在的小时
        long totalHour = totalMinutes / 60;
        long currentHour = totalHour % 24;

        //显示时间
        System.out.println("总毫秒为： " + totalMilliSeconds);
        System.out.println(currentHour + ":" + currentMinute + ":" + currentSecond + " GMT");


        return TimeUtils.getFitTimeSpan(date.getTime(), totalMilliSeconds, 4);


//        Date nowTime = new Date(ss);
//        System.out.println(System.currentTimeMillis());
//        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
//        return sdFormatter.format(nowTime);
    }

}
