package debug;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.core.base.BaseModuleApplication;

/**
 * <p></p>
 *
 * @author: mcgrady
 * @date: 2018/5/28
 */
@Route(path = "/laboratory/LabApp")
public class LabApp extends BaseModuleApplication {

    @Override
    public void onCreateAsLibrary(Application application) {
        super.onCreateAsLibrary(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
