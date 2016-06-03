package in.creativebucket.weefit.presetworkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;
import in.creativebucket.weefit.dto.PresetWorkout;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/5/2015.
 */

public class PresetExerciseSelectActivity extends Activity implements AppConstants {

    public Bundle bundleData;
    private int gymExImage;
    private ImageView exerciseImageView, imgStartExercise;
    private DatabaseHelper database;
    private ArrayList<PresetWorkout> presetWorkoutlist;
    private int workoutId, exercisePos;
    private int[] exerciseImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_workout_select);
        bundleData = getIntent().getExtras();
        workoutId = bundleData.getInt(PRESET_WORKOUT_ID);
        exercisePos = bundleData.getInt(PRESET_EXERCISE_POS);
        database = new DatabaseHelper(this);
        exerciseImages = new ExerciseImagesAndData().presetWorkoutExerciseList(workoutId);
        presetWorkoutlist = database.getAllPresetWorkout(workoutId);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    public void setUIonScreen(WatchViewStub stub) {
        exerciseImageView = (ImageView) stub.findViewById(R.id.exercise_picture);
        imgStartExercise = (ImageView) stub.findViewById(R.id.img_start_exercise);

        if (exercisePos >= exerciseImages.length) {
            Toast.makeText(this, getResources().getString(R.string.all_done), Toast.LENGTH_LONG).show();
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
        } else {
            exerciseImageView.setBackground(null);
            exerciseImageView.setBackgroundResource(exerciseImages[exercisePos]);
        }
        imgStartExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManager.getInstance(getApplicationContext()).setCurrentExerciseTimer(null);
                PreferencesManager.getInstance(getApplicationContext()).setTimerStatus(0);
                PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
                PreferencesManager.getInstance(getApplicationContext()).setCurrentSetNumber(Integer.parseInt(presetWorkoutlist.get(exercisePos).getSets()));
                PreferencesManager.getInstance(getApplicationContext()).setIsAllowMessage(true);

                Intent intent = new Intent(getApplicationContext(), PresetExerciseStartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putAll(bundleData);
                bundle.putInt(PRESET_EXERCISE, exerciseImages[exercisePos]);
                bundle.putInt(PRESET_EX_UID, presetWorkoutlist.get(exercisePos).getUniqueId());
                bundle.putString(PRESET_EX_TIME, presetWorkoutlist.get(exercisePos).getTime());
                bundle.putString(PRESET_EX_REPS, presetWorkoutlist.get(exercisePos).getReps());
                bundle.putString(PRESET_EX_SET, presetWorkoutlist.get(exercisePos).getSets());
                bundle.putString(PRESET_EX_CALORY, presetWorkoutlist.get(exercisePos).getCalory());
                bundle.putString(PRESET_EX_REST, presetWorkoutlist.get(exercisePos).getRest());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });


    }
}
