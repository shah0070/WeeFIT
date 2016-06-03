package in.creativebucket.weefit.adapter;

/**
 * Created by Chandan kumar on 7/5/2015.
 */


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.creativebucket.weefit.createworkout.CreateWorkoutExerciseFragment;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.Row;

public class CreateWorkoutGridPagerAdapter extends FragmentGridPagerAdapter implements AppConstants {
    private static final int TRANSITION_DURATION_MILLIS = 100;

    private final Context mContext;
    private List<Row> mRows;
    private int[] createWorkoutResources;
    private String categoryName;
    private boolean is_from_queue;

    public CreateWorkoutGridPagerAdapter(Context ctx, FragmentManager fm, int[] createWorkoutResources, String categoryName, boolean is_from_queue) {
        super(fm);
        this.mContext = ctx;
        this.createWorkoutResources = createWorkoutResources;
        this.categoryName = categoryName;
        this.is_from_queue = is_from_queue;

        mRows = new ArrayList<>();

        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < createWorkoutResources.length; i++) {
            CreateWorkoutExerciseFragment createWorkoutExerciseFragment = new CreateWorkoutExerciseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(CREATE_WORKOUT_EXERCISE, createWorkoutResources[i]);
            bundle.putString(CATEGORY_NAME, categoryName);
            bundle.putInt(CREATED_WORKOUT_POS, i);
            bundle.putBoolean(IS_FROM_QUEUE, is_from_queue);
            createWorkoutExerciseFragment.setArguments(bundle);
            fragments.add(createWorkoutExerciseFragment);
        }
        mRows.add(new Row(fragments));
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row);
        return adapterRow.getColumn(col);
    }

    @Override
    public int getRowCount() {
        return mRows.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount();
    }

}
