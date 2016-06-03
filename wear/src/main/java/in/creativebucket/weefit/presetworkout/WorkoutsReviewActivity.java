package in.creativebucket.weefit.presetworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.creativebucket.weefit.MainActivity;
import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CreateWorkoutData;
import in.creativebucket.weefit.dto.PresetWorkout;

/**
 * Created by Chandan kumar on 7/5/2015.
 */
public class WorkoutsReviewActivity extends Activity implements AppConstants {

    private Bundle bundleData;
    private int workoutId, workoutType;
    private TextView totalTimeTxtVw, totalCaloryTxtVw, totalSetsTxtVw;
    private DatabaseHelper database;
    private ArrayList<PresetWorkout> presetWorkoutData;
    private ArrayList<CreateWorkoutData> createWorkoutList;
    private int totalTime, totalSets, totalReps;
    private LinearLayout parentLayout;
    private double totalCalory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_workout_review);
        database = new DatabaseHelper(this);

        bundleData = getIntent().getExtras();
        workoutId = bundleData.getInt(PRESET_WORKOUT_ID);
        workoutType = bundleData.getInt(WORKOUT_TYPE);

        if (workoutType == WORKOUT_TYPE_PRESET) {
            presetWorkoutData = database.getAllPresetWorkout(workoutId);
        } else {
            createWorkoutList = database.getAllCreatedWorkouts();
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    private void setUIonScreen(WatchViewStub stub) {
        parentLayout = (LinearLayout) stub.findViewById(R.id.mainLayout);
        totalTimeTxtVw = (TextView) stub.findViewById(R.id.total_time);
        totalCaloryTxtVw = (TextView) stub.findViewById(R.id.total_calory);
        totalSetsTxtVw = (TextView) stub.findViewById(R.id.total_sets);

        if (workoutType == WORKOUT_TYPE_PRESET) {

            for (int i = 0; i < presetWorkoutData.size(); i++) {
                PresetWorkout presetWorkout = presetWorkoutData.get(i);
                int timeValue = getTimeInSeconds(presetWorkout.getTime()) + getTimeInSeconds(presetWorkout.getRest());
                int caloryValue = Integer.parseInt(presetWorkout.getCalory());
                int setsValue = Integer.parseInt(presetWorkout.getSets());
                int repsValue = Integer.parseInt(presetWorkout.getReps());

                totalTime = totalTime + timeValue * Integer.parseInt(presetWorkout.getSets());
                totalSets = totalSets + setsValue;
                if (repsValue > 0)
                    totalCalory = totalCalory + setsValue * repsValue * caloryValue;
                else
                    totalCalory = totalCalory + setsValue * caloryValue;
            }

            int totalMin = totalTime / 60;
            int totalSec = totalTime % 60;
            String strTotalTime = totalMin + " Min " + totalSec + " Sec";
            totalTimeTxtVw.setText(strTotalTime);

            if (totalSets < 10) {
                totalSetsTxtVw.setText("0" + totalSets + " Sets");
            } else {
                totalSetsTxtVw.setText("" + totalSets + " Sets");
            }

            totalCaloryTxtVw.setText(String.format("%.2f", totalCalory) + " Kcal");

        } else {
            for (int i = 0; i < createWorkoutList.size(); i++) {
                CreateWorkoutData createWorkoutData = createWorkoutList.get(i);
                int timeValue = getTimeInSeconds(createWorkoutData.getWorkoutTime()) + getTimeInSeconds(createWorkoutData.getWorkoutRestTime());
                int caloryValue = Integer.parseInt(createWorkoutData.getWorkoutCalory());
                int setsValue = Integer.parseInt(createWorkoutData.getWorkoutSets());
                int repsValue = Integer.parseInt(createWorkoutData.getWorkoutReps());

                totalTime = totalTime + timeValue * Integer.parseInt(createWorkoutData.getWorkoutSets());
                totalSets = totalSets + setsValue;
                totalReps = totalReps + repsValue;
            }

            int totalMin = totalTime / 60;
            int totalSec = totalTime % 60;
            String strTotalTime = totalMin + " Min " + totalSec + " Sec";
            totalTimeTxtVw.setText(strTotalTime);

            if (totalSets < 10) {
                totalSetsTxtVw.setText("0" + totalSets + " Sets");
            } else {
                totalSetsTxtVw.setText("" + totalSets + " Sets");
            }
            if (totalReps > 0)
                totalCalory = totalSets * totalReps * 0.2;
            else
                totalCalory = totalSets * 0.2;


            totalCaloryTxtVw.setText(String.format("%.2f", totalCalory) + " Kcal");
        }

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutsReviewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    private int getTimeInSeconds(String exTime) {
        String[] strArr = exTime.split(":");
        int exTimeInSeconds = Integer.parseInt(strArr[0]) * 60 + Integer.parseInt(strArr[1]);
        return exTimeInSeconds;
    }


}
