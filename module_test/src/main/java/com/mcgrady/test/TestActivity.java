package com.mcgrady.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_res.widget.recyclerpage.HorizontalPageLayoutManager;
import com.mcgrady.common_res.widget.recyclerpage.PagingSlideHelper;

import java.util.ArrayList;
import java.util.List;

import static com.mcgrady.test.R.id.test_vp_tab;

public class TestActivity extends AppCompatActivity {

    public static final String TAG = TestActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private PagingSlideHelper pagingSlideHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        setContentView(R.layout.test_activity_sliding_icontab);

        recyclerView = findViewById(test_vp_tab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        List<String> list = new ArrayList<>();
        list.add("sss");
        list.add("eee");
        list.add("ddd");
        list.add("qqq");
        list.add("ttt");
        list.add("uuu");
        recyclerView.setAdapter(new TabAdapter(R.layout.item_menu_tab, list));
        pagingSlideHelper = new PagingSlideHelper();
        pagingSlideHelper.setRecyclerView(recyclerView);
        pagingSlideHelper.setOnPageChangeListener(new PagingSlideHelper.onPageChangeListener() {
            @Override
            public void onPageChange(int index) {

                LogUtils.d(index);
            }
        });
        recyclerView.setLayoutManager(new HorizontalPageLayoutManager(1, 3));
        pagingSlideHelper.updateLayoutManger();
        pagingSlideHelper.scrollToPosition(0);
        recyclerView.setHorizontalScrollBarEnabled(true);

    }


    private class TabAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TabAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

}
