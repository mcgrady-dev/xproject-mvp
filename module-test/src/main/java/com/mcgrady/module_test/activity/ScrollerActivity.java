package com.mcgrady.module_test.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrollerActivity extends AppCompatActivity {

    ListView listView;
    ScrollView scrollView;

    private String[] names = new String[]{"B神", "基神", "曹神"};
    private String[] says = new String[]{"无形被黑，最为致命", "大神好厉害~", "我将带头日狗~"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        scrollView = findViewById(R.id.scrollView);
        listView = findViewById(R.id.listview);

        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int j = 0; j <= 10; j++) {
            for (int i = 0; i < names.length; i++) {
                Map<String, Object> showitem = new HashMap<String, Object>();
                showitem.put("name", names[i] + String.valueOf(j) + String.valueOf(i));
                showitem.put("says", says[i] + String.valueOf(j) + String.valueOf(i));
                listitem.add(showitem);
            }
        }

        listView.setAdapter(new SimpleAdapter(this,
                listitem,
                android.R.layout.simple_list_item_activated_2,
                new String[]{"name", "says"},
                new int[]{android.R.id.text1, android.R.id.text2}));

        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View itemView = listAdapter.getView(i, null, listView);
            itemView.measure(0, 0);
            totalHeight += itemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200);
    }
}