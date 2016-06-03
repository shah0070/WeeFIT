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
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.Row;
import in.creativebucket.weefit.presetworkout.PresetExerciseFragment;

/**
 * Constructs fragments as requested by the GridViewPager. For each row a different background is
 * provided.
 * <p/>
 * Always avoid loading resources from the main thread. In this sample, the background images are
 * loaded from an background task and then updated using {@link #notifyRowBackgroundChanged(int)}
 * and {@link #notifyPageBackgroundChanged(int, int)}.
 */
public class PresetExerciseGridPagerAdapter extends FragmentGridPagerAdapter implements AppConstants {
    private static final int TRANSITION_DURATION_MILLIS = 100;

    private final Context mContext;
    private List<Row> mRows;
    private int[] presetExSources;

    public PresetExerciseGridPagerAdapter(Context ctx, FragmentManager fm, int[] presetExSources) {
        super(fm);
        this.mContext = ctx;
        this.presetExSources = presetExSources;
        mRows = new ArrayList<Row>();

        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < presetExSources.length; i++) {
            PresetExerciseFragment presetExFragment = new PresetExerciseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(PRESET_EXERCISE, presetExSources[i]);
            bundle.putInt(PRESET_WORKOUT_ID, i + 1);
            bundle.putInt(PRESET_EXERCISE_POS, 0);
            presetExFragment.setArguments(bundle);

            fragments.add(presetExFragment);
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
