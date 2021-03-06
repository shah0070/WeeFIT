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

package in.creativebucket.weefit.createworkout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;


public class CreateWorkoutFragment extends Fragment {

    private DatabaseHelper database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_workout, container, false);
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
        ImageView imageView = (ImageView) stub.findViewById(R.id.create_workout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.removeAllCreatedWorkouts();
                Intent intent = new Intent(getActivity(), CreateWorkoutCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
