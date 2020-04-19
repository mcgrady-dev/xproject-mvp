package com.mcgrady.common_core.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.R;

/**
 * Created by mcgrady on 2019/5/6.
 */
public class TestSwitchKit implements IKit {

    @Override
    public int getCategory() {
        return Category.BIZ;
    }

    @Override
    public int getName() {
        return R.string.test_switch;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_jpg_icon;
    }

    @Override
    public void onClick(Context context) {
        Toast.makeText(context, "test switch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppInit(Context context) {
        Log.d("TestSwitchKit", "test init switch kit");
    }
}
