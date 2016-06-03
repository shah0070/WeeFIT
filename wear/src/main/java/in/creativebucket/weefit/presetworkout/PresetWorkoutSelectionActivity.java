package in.creativebucket.weefit.presetworkout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.adapter.PresetExerciseGridPagerAdapter;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;

/**
 * Created by Chandan kumar on 6/27/2015.
 */
public class PresetWorkoutSelectionActivity extends Activity implements AppConstants, GridViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private GridViewPager mGridPager;
    private int[] mWorkoutResources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_exercise_selection);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    public void setUIonScreen(WatchViewStub stub) {
        setPresetExerciseImages();
        mGridPager = (GridViewPager) stub.findViewById(R.id.gym_exercise_pager);
        mGridPager.setAdapter(new PresetExerciseGridPagerAdapter(this, getFragmentManager(), mWorkoutResources));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.gym_page_indicator);
        dotsPageIndicator.setPager(mGridPager);
        mGridPager.setOnPageChangeListener(this);
        onPageSelected(0, 0);
    }

    public void setPresetExerciseImages() {
        mWorkoutResources = new ExerciseImagesAndData().pressetWorkoutList();
    }

    @Override
    public void onPageScrolled(int i, int i2, float v, float v2, int i3, int i4) {

    }

    @Override
    public void onPageSelected(int row, int column) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
