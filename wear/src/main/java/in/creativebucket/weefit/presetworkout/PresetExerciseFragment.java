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

package in.creativebucket.weefit.presetworkout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.dto.AppConstants;

public class PresetExerciseFragment extends Fragment implements AppConstants {

    public Bundle bundle;
    private int workoutImage, workoutId, exercisePos;
    private String presetExTime, presetExReps, presetExSets, presetExCalory;
    private ImageView exerciseImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        workoutImage = bundle.getInt(PRESET_EXERCISE);
        workoutId = bundle.getInt(PRESET_WORKOUT_ID);
        exercisePos = bundle.getInt(PRESET_EXERCISE_POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preset_exercise_fragment, container, false);
        final WatchViewStub stub = (WatchViewStub) rootView.findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
        return rootView;
    }


    public void setUIonScreen(WatchViewStub stub) {
        exerciseImage = (ImageView) stub.findViewById(R.id.exercise_picture);
        exerciseImage.setBackgroundResource(workoutImage);
        exerciseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PresetExerciseSelectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });
    }
}
