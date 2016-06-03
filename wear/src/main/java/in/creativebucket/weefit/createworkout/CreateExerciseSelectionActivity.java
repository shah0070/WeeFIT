package in.creativebucket.weefit.createworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.adapter.CreateWorkoutGridPagerAdapter;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 6/27/2015.
 */
public class CreateExerciseSelectionActivity extends Activity implements AppConstants, GridViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private GridViewPager mGridPager;
    private String categoryName;
    private int[] mGymResources;
    private CreateWorkoutGridPagerAdapter adapter;
    private boolean is_from_queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_exercise_selection);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra(CATEGORY_NAME);
        is_from_queue = intent.getBooleanExtra(IS_FROM_QUEUE, false);

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
        adapter = new CreateWorkoutGridPagerAdapter(this, getFragmentManager(), mGymResources, categoryName, is_from_queue);
        mGridPager.setAdapter(adapter);
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.gym_page_indicator);
        dotsPageIndicator.setPager(mGridPager);
        mGridPager.setOnPageChangeListener(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int workoutPos = PreferencesManager.getInstance(getApplicationContext()).getNextExercisePos();
                mGridPager.setCurrentItem(0, workoutPos);
            }
        });

    }

    public void setPresetExerciseImages() {
        mGymResources = new ExerciseImagesAndData().createWorkoutExerciseList(getApplicationContext(), categoryName);
    }

    @Override

    public void onPageScrolled(int i, int i2, float v, float v2, int i3, int i4) {

    }

    @Override
    public void onPageSelected(int row, int column) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        adapter.notifyDataSetChanged();
    }
}
