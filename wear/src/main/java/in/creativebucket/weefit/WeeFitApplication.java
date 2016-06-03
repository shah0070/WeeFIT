package in.creativebucket.weefit;


import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Chandan kumar
 */
public class WeeFitApplication extends Application {

    private static WeeFitApplication appInstance;
    private ExecutorService mSharedPreferanceExecutor;

    public static WeeFitApplication getInstance() {
        return appInstance;
    }

    public static WeeFitApplication getApplication(Context context) {
        return (WeeFitApplication) context.getApplicationContext();
    }

    public static WeeFitApplication getApplication() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public ExecutorService getPreferenceExecutor() {
        if (null == mSharedPreferanceExecutor || mSharedPreferanceExecutor.isShutdown()) {
            mSharedPreferanceExecutor = Executors.newSingleThreadExecutor();
        }
        return mSharedPreferanceExecutor;
    }

}
