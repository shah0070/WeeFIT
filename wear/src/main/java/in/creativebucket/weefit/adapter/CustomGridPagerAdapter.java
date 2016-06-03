/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.creativebucket.weefit.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.creativebucket.weefit.createworkout.CreateWorkoutFragment;
import in.creativebucket.weefit.dto.Row;
import in.creativebucket.weefit.presetworkout.PresetWorkoutFragment;

/**
 * Constructs fragments as requested by the GridViewPager. For each row a different background is
 * provided.
 * <p/>
 * Always avoid loading resources from the main thread. In this sample, the background images are
 * loaded from an background task and then updated using {@link #notifyRowBackgroundChanged(int)}
 * and {@link #notifyPageBackgroundChanged(int, int)}.
 */
public class CustomGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final int TRANSITION_DURATION_MILLIS = 100;

    private final Context mContext;
    private List<Row> mRows;

    public CustomGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;

        mRows = new ArrayList<Row>();

        mRows.add(new Row(new PresetWorkoutFragment(), new CreateWorkoutFragment()));
        // mRows.add(new Row(new CustomFragment(), new GymCategoryFragment(), new TableExerciseFragment()));
        //mRows.add(new Row(new CustomFragment(), new GymCategoryFragment(), new TableExerciseFragment()));

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
