package com.mcgrady.module_test.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;
import com.mcgrady.module_test.model.TestModel;

import java.util.List;
import java.util.Map;

public class NestedScrollerActivity extends AppCompatActivity {

    ListView listView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroller);

        scrollView = findViewById(R.id.scrollView);
        listView = findViewById(R.id.listview);

        List<Map<String, Object>> listitem = new TestModel().getSimpleListData(10);
        listView.setAdapter(new SimpleAdapter(this,
                listitem,
                android.R.layout.simple_list_item_activated_2,
                new String[]{"name", "says"},
                new int[]{android.R.id.text1, android.R.id.text2}));


    }
}