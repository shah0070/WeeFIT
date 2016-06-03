package in.creativebucket.weefit;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;

import in.creativebucket.weefit.adapter.CustomGridPagerAdapter;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;
import in.creativebucket.weefit.util.PreferencesManager;

public class MainActivity extends Activity implements AppConstants {

    private GridViewPager mGridPager;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabaseHelper(getApplicationContext());
        boolean isFirstLaunch = PreferencesManager.getInstance(this).getIsFirstLaunch();
        if (isFirstLaunch) {
            PreferencesManager.getInstance(this).setIsFirstLaunch(false);
            setUpPresetWorkoutList();
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    public void setUIonScreen(WatchViewStub stub) {
        mGridPager = (GridViewPager) stub.findViewById(R.id.pager);
        mGridPager.setAdapter(new CustomGridPagerAdapter(this, getFragmentManager()));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(mGridPager);
    }

    private void setUpPresetWorkoutList() {
        database.insertPresetWorkoutInDb(new ExerciseImagesAndData().getWorkoutExerciseList());
    }
}

