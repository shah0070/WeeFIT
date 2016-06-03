package in.creativebucket.weefit.createworkout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CreateWorkoutData;
import in.creativebucket.weefit.ui.PlayPauseStopActivity;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/5/2015.
 */
public class CreatedExerciseRestActivity extends Activity implements AppConstants {

    private Bundle bundleData;
    private String createdExRestTime, createdExSets, createdExReps;
    private int createdExImage, createdExPos, workoutId;
    private TextView exRestTxtvw, exRepTxtvw, exSetsTxtvw;
    private ImageView restImageView, restPlayPauseView;
    private CustomCountDownTimer timer;
    private DatabaseHelper database;
    private ArrayList<CreateWorkoutData> createdWorkoutList;
    private int nextCreatedExPos;
    private int[] exerciseImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_exercise_rest);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        database = new DatabaseHelper(this);

        bundleData = getIntent().getExtras();
        workoutId = bundleData.getInt(CREATED_WORKOUT_ID);
        createdExImage = bundleData.getInt(CREATED_EXERCISE_IMAGE);
        createdExSets = bundleData.getString(CREATED_EX_SETS);
        createdExReps = bundleData.getString(CREATED_EX_REPS);
        createdExPos = bundleData.getInt(CREATED_WORKOUT_POS);
        createdExRestTime = bundleData.getString(CREATED_EX_REST);
        nextCreatedExPos = createdExPos + 1;
        createdWorkoutList = database.getAllCreatedWorkouts();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    private void setUIonScreen(WatchViewStub stub) {
        restImageView = (ImageView) stub.findViewById(R.id.rest_image);
        exRestTxtvw = (TextView) stub.findViewById(R.id.rest_time);
        exRepTxtvw = (TextView) stub.findViewById(R.id.exercise_repos);
        exSetsTxtvw = (TextView) stub.findViewById(R.id.exercise_sets);
        restPlayPauseView = (ImageView) stub.findViewById(R.id.play_pause);

        restImageView.setBackgroundResource(createdExImage);
        exRestTxtvw.setText(createdExRestTime);

        String setNumbr = String.valueOf(PreferencesManager.getInstance(this).getCurrentSetNumber());

        if (setNumbr.length() > 2) {
            exSetsTxtvw.setText(setNumbr);
        } else if (setNumbr.length() == 2) {
            exSetsTxtvw.setText("0" + setNumbr);
        } else {
            exSetsTxtvw.setText("00" + setNumbr);
        }

        if (createdExReps.length() > 2) {
            exRepTxtvw.setText(createdExReps);
        } else if (createdExReps.length() == 2) {
            exRepTxtvw.setText("0" + createdExReps);
        } else {
            exRepTxtvw.setText("00" + createdExReps);
        }

        if (!createdExRestTime.equals("00:00")) {
            long exerciseTimeInMillis = getTimeInMilliseconds(createdExRestTime);
            timer = new CustomCountDownTimer(exerciseTimeInMillis, 1000);
            timer.start();
        }

        final GestureDetector gdt = new GestureDetector(new GestureListener());

        restPlayPauseView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });

    }

    public long getTimeInMilliseconds(String restTime) {
        String[] arr = restTime.split(":");
        long timeInMillis = Integer.parseInt(arr[0]) * 60 * 1000 + Integer.parseInt(arr[1]) * 1000;
        return timeInMillis;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String restRemainTime = PreferencesManager.getInstance(getApplicationContext()).getCurrentRestTimer();
        int timerStatus = PreferencesManager.getInstance(getApplicationContext()).getTimerStatus();

        if (restRemainTime != null && !restRemainTime.equals("0") && timerStatus == 2) {
            if (timer != null)
                timer = null;
            long remainTimeInMillis = Long.parseLong(restRemainTime);
            timer = new CustomCountDownTimer(remainTimeInMillis, 1000);
            timer.start();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void startExerciseScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int currentSetNum = PreferencesManager.getInstance(getApplicationContext()).getCurrentSetNumber();
        if (currentSetNum > 0) {
            PreferencesManager.getInstance(getApplicationContext()).setCurrentSetNumber(currentSetNum - 1);
            Intent intent = new Intent(getApplicationContext(), CreatedExerciseStartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putAll(bundleData);
            bundle.putInt(PRESET_WORKOUT_ID, workoutId);
            bundle.putInt(PRESET_EXERCISE_POS, createdExPos);
            intent.putExtras(bundle);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    private class CustomCountDownTimer extends CountDownTimer {

        public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onFinish() {
            String timeStr = exRestTxtvw.getText().toString();
            if (timeStr.equals("00:01")) {
                exRestTxtvw.setText("00:00");
                startExerciseScreen();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            PreferencesManager.getInstance(getApplicationContext()).setCurrentRestTimer(String.valueOf(millisUntilFinished));

            long remainingSeconds = millisUntilFinished / 1000;
            long minute = remainingSeconds / 60;
            long sec = remainingSeconds % 60;
            if (exRestTxtvw != null) {
                if (minute >= 10 && sec >= 10)
                    exRestTxtvw.setText(minute + ":" + sec);
                else if (minute >= 10 && sec < 10)
                    exRestTxtvw.setText(minute + ":0" + sec);
                else if (minute < 10 && sec >= 10)
                    exRestTxtvw.setText("0" + minute + ":" + sec);
                else
                    exRestTxtvw.setText("0" + minute + ":0" + sec);
            }

//            timeElapsed = startTime - millisUntilFinished;
//            time_eplapsed.setText("Time Elapsed: "
//                    + String.valueOf(timeElapsed));
        }

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Right to left
//            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Left to right
//            }

//            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Bottom to top
//            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Top to bottom
//            }

            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                Intent intent = new Intent(CreatedExerciseRestActivity.this, PlayPauseStopActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.top_in, 0);
                return false; // Top to bottom
            }
            return false;
        }
    }
}
