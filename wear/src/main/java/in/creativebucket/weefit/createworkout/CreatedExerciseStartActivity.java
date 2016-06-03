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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CreateWorkoutData;
import in.creativebucket.weefit.presetworkout.WorkoutsReviewActivity;
import in.creativebucket.weefit.ui.PlayPauseStopActivity;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/5/2015.
 */
public class CreatedExerciseStartActivity extends Activity implements AppConstants {

    private Bundle bundleData;
    private String createdExTime, createdExReps, createdExSets, createdExCalory, createdExRestTime, workoutCategory;
    private int createdExImage, createdExPos, workoutId, nextCreatedExPos;
    private TextView exTimeTxtvw, exRepTxtvw, exSetsTxtvw;
    private ImageView exImageView, exPlayPauseView;
    private CustomCountDownTimer countDownTimer;
    private DatabaseHelper database;
    private Timer countUpTimer;
    private ArrayList<CreateWorkoutData> createdWorkoutList;
    private int countUpValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_exercise_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        database = new DatabaseHelper(getApplicationContext());
        createdWorkoutList = database.getAllCreatedWorkouts();

        bundleData = getIntent().getExtras();

        createdExPos = bundleData.getInt(CREATED_WORKOUT_POS);
        workoutId = bundleData.getInt(CREATED_WORKOUT_ID);
        createdExImage = bundleData.getInt(CREATED_EXERCISE_IMAGE);
        createdExTime = bundleData.getString(CREATED_EX_TIME);
        createdExReps = bundleData.getString(CREATED_EX_REPS);
        createdExSets = bundleData.getString(CREATED_EX_SETS);
        createdExCalory = bundleData.getString(CREATED_EX_CALORY);
        createdExRestTime = bundleData.getString(CREATED_EX_REST);

        nextCreatedExPos = PreferencesManager.getInstance(getApplicationContext()).getNextCreatedWorkoutPos();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    private void setUIonScreen(WatchViewStub stub) {
        exImageView = (ImageView) stub.findViewById(R.id.exercise_image);
        exTimeTxtvw = (TextView) stub.findViewById(R.id.exercise_time);
        exRepTxtvw = (TextView) stub.findViewById(R.id.exercise_repos);
        exSetsTxtvw = (TextView) stub.findViewById(R.id.exercise_sets);
        exPlayPauseView = (ImageView) stub.findViewById(R.id.play_pause);

        int currSetNumber = PreferencesManager.getInstance(getApplicationContext()).getCurrentSetNumber();

        if (nextCreatedExPos >= createdWorkoutList.size() - 1 && currSetNumber == 0) {
            Toast.makeText(this, "done", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, WorkoutsReviewActivity.class);
            intent.putExtra(PRESET_WORKOUT_ID, 0);
            intent.putExtra(WORKOUT_TYPE, WORKOUT_TYPE_CREATED);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else if (nextCreatedExPos < createdWorkoutList.size() && currSetNumber == 0) {
            nextCreatedExPos += 1;
            PreferencesManager.getInstance(getApplicationContext()).setNextCreatedWorkoutPos(nextCreatedExPos);
            CreateWorkoutData nextWorkoutData = createdWorkoutList.get(nextCreatedExPos);
            PreferencesManager.getInstance(getApplicationContext()).setNextExercisePos(nextWorkoutData.getPosition());
            Intent intent = new Intent(getApplicationContext(), CreateExerciseSelectionActivity.class);
            intent.putExtra(CATEGORY_NAME, nextWorkoutData.getCategory());
            intent.putExtra(IS_FROM_QUEUE, true);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

        } else if (currSetNumber <= 0) {
            Intent intent = new Intent(getApplicationContext(), CreateWorkoutCategoryActivity.class);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }

        exImageView.setBackgroundResource(createdExImage);
        exTimeTxtvw.setText(createdExTime);

        if (createdExTime.equals("00:00") || createdExTime.equals("0:0")) {
            exTimeTxtvw.setText("00:00");
        } else {
            exTimeTxtvw.setText(createdExTime);
        }

        if (createdExReps.length() > 2) {
            exRepTxtvw.setText(createdExReps);
        } else if (createdExReps.length() == 2) {
            exRepTxtvw.setText("0" + createdExReps);
        } else {
            exRepTxtvw.setText("00" + createdExReps);
        }

        String currSetNumberStr = String.valueOf(PreferencesManager.getInstance(getApplicationContext()).getCurrentSetNumber());

        if (currSetNumberStr.length() > 2) {
            exSetsTxtvw.setText(currSetNumberStr);
        } else if (currSetNumberStr.length() == 2) {
            exSetsTxtvw.setText("0" + currSetNumberStr);
        } else {
            exSetsTxtvw.setText("00" + currSetNumberStr);
        }


        if (!createdExTime.equals("00:00") && !createdExTime.equals("0:0")) {
            long exerciseTimeInMillis = getTimeInMilliseconds(createdExTime);
            countDownTimer = new CustomCountDownTimer(exerciseTimeInMillis, 1000);
            countDownTimer.start();
        } else {

            countUpTimer = new Timer();

            countUpTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long minute = countUpValue / 60;
                            long sec = countUpValue % 60;

                            if (minute >= 10 && sec >= 10)
                                exTimeTxtvw.setText(minute + ":" + sec);
                            else if (minute >= 10 && sec < 10)
                                exTimeTxtvw.setText(minute + ":0" + sec);
                            else if (minute < 10 && sec >= 10)
                                exTimeTxtvw.setText("0" + minute + ":" + sec);
                            else
                                exTimeTxtvw.setText("0" + minute + ":0" + sec);

                            countUpValue++;
                        }
                    });
                }
            }, 1000, 1000);

            exImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    long minute = countUpValue / 60;
                    long sec = countUpValue % 60;
                    String workoutTimeString = minute + ":" + sec;
                    database.updateCreatedWorkoutTime(workoutTimeString, workoutId);
                    PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
                    Intent intent = new Intent(getApplicationContext(), CreatedExerciseRestActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putAll(bundleData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibe.vibrate(1000);
                    finish();
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    return false;
                }
            });

            boolean isShowMessage = PreferencesManager.getInstance(getApplicationContext()).getIsAllowMessage();

            if (isShowMessage) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_to_move_on), Toast.LENGTH_LONG).show();
                PreferencesManager.getInstance(getApplicationContext()).setIsAllowMessage(false);
            }

        }

        final GestureDetector gdt = new GestureDetector(new GestureListener());

        exPlayPauseView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });
    }

    public long getTimeInMilliseconds(String exerciseTime) {
        String[] arr = exerciseTime.split(":");
        long timeInMillis = Integer.parseInt(arr[0]) * 60 * 1000 + Integer.parseInt(arr[1]) * 1000;
        return timeInMillis;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (countUpTimer != null) {
            countUpTimer.cancel();
            countUpTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String exRemainTime = PreferencesManager.getInstance(getApplicationContext()).getCurrentExerciseTimer();
        int timerStatus = PreferencesManager.getInstance(getApplicationContext()).getTimerStatus();

        if (exRemainTime != null && !exRemainTime.equals("0") && timerStatus == 2) {
            if (countDownTimer != null)
                countDownTimer = null;
            long remainTimeInMillis = Long.parseLong(exRemainTime);
            countDownTimer = new CustomCountDownTimer(remainTimeInMillis, 1000);
            countDownTimer.start();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public void startRestScreen() {
        PreferencesManager.getInstance(getApplicationContext()).setCurrentRestTimer(null);
        PreferencesManager.getInstance(getApplicationContext()).setTimerStatus(0);
        PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
        Intent intent = new Intent(getApplicationContext(), CreatedExerciseRestActivity.class);
        Bundle bundle = new Bundle();
        bundle.putAll(bundleData);
        intent.putExtras(bundle);
        startActivity(intent);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(1000);
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }


    private class CustomCountDownTimer extends CountDownTimer {

        public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            String timeStr = exTimeTxtvw.getText().toString();
            if (timeStr.equals("00:01")) {
                exTimeTxtvw.setText("00:00");
                startRestScreen();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            PreferencesManager.getInstance(getApplicationContext()).setCurrentExerciseTimer(String.valueOf(millisUntilFinished));

            long remainingSeconds = millisUntilFinished / 1000;
            long minute = remainingSeconds / 60;
            long sec = remainingSeconds % 60;
            if (exTimeTxtvw != null) {
                if (minute >= 10 && sec >= 10)
                    exTimeTxtvw.setText(minute + ":" + sec);
                else if (minute >= 10 && sec < 10)
                    exTimeTxtvw.setText(minute + ":0" + sec);
                else if (minute < 10 && sec >= 10)
                    exTimeTxtvw.setText("0" + minute + ":" + sec);
                else
                    exTimeTxtvw.setText("0" + minute + ":0" + sec);
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
                Intent intent = new Intent(CreatedExerciseStartActivity.this, PlayPauseStopActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.top_in, 0);
                return false; // Top to bottom
            }
            return false;
        }
    }
}
