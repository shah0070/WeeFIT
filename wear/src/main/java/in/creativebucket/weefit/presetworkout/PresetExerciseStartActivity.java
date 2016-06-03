package in.creativebucket.weefit.presetworkout;

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

import java.util.Timer;
import java.util.TimerTask;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;
import in.creativebucket.weefit.ui.PlayPauseStopActivity;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/5/2015.
 */
public class PresetExerciseStartActivity extends Activity implements AppConstants {

    private Bundle bundleData;
    private String presetExTime, presetExReps, presetExSets, presetExCalory, presetExRestTime;
    private int presetExImage, presetExPos, workoutId, nextPresetExPos, workoutUid;
    private TextView exTimeTxtvw, exRepTxtvw, exSetsTxtvw;
    private ImageView exImageView, exPlayPauseView;
    private CustomCountDownTimer countDownTimer;
    private Timer countUpTimer;
    private int countUpValue = 0;
    private int[] exerciseImages;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preset_exercise_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        database = new DatabaseHelper(getApplicationContext());

        bundleData = getIntent().getExtras();
        workoutUid = bundleData.getInt(PRESET_EX_UID);
        workoutId = bundleData.getInt(PRESET_WORKOUT_ID);
        presetExPos = bundleData.getInt(PRESET_EXERCISE_POS);
        presetExImage = bundleData.getInt(PRESET_EXERCISE);
        presetExTime = bundleData.getString(PRESET_EX_TIME);
        presetExReps = bundleData.getString(PRESET_EX_REPS);
        presetExSets = bundleData.getString(PRESET_EX_SET);
        presetExCalory = bundleData.getString(PRESET_EX_CALORY);
        presetExRestTime = bundleData.getString(PRESET_EX_REST);
        nextPresetExPos = presetExPos + 1;

        exerciseImages = new ExerciseImagesAndData().presetWorkoutExerciseList(workoutId);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    private void setUIonScreen(WatchViewStub stub) {

        int currSetNumber = PreferencesManager.getInstance(getApplicationContext()).getCurrentSetNumber();

        if (nextPresetExPos >= exerciseImages.length && currSetNumber == 0) {
            Toast.makeText(this, "done", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, WorkoutsReviewActivity.class);
            intent.putExtra(PRESET_WORKOUT_ID, workoutId);
            intent.putExtra(WORKOUT_TYPE, WORKOUT_TYPE_PRESET);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else if (currSetNumber <= 0) {
            Intent intent = new Intent(getApplicationContext(), PresetExerciseSelectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(PRESET_WORKOUT_ID, workoutId);
            bundle.putInt(PRESET_EXERCISE_POS, nextPresetExPos);
            intent.putExtras(bundle);
            startActivity(intent);
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }

        exImageView = (ImageView) stub.findViewById(R.id.exercise_image);
        exTimeTxtvw = (TextView) stub.findViewById(R.id.exercise_time);
        exRepTxtvw = (TextView) stub.findViewById(R.id.exercise_repos);
        exSetsTxtvw = (TextView) stub.findViewById(R.id.exercise_sets);
        exPlayPauseView = (ImageView) stub.findViewById(R.id.play_pause);

        exImageView.setBackgroundResource(presetExImage);

        exTimeTxtvw.setText(presetExTime);

        String currSetNumberStr = String.valueOf(PreferencesManager.getInstance(getApplicationContext()).getCurrentSetNumber());

        if (currSetNumberStr.length() > 2) {
            exSetsTxtvw.setText(currSetNumberStr);
        } else if (currSetNumberStr.length() == 2) {
            exSetsTxtvw.setText("0" + currSetNumberStr);
        } else {
            exSetsTxtvw.setText("00" + currSetNumberStr);
        }


        if (presetExReps.length() > 2) {
            exRepTxtvw.setText(presetExReps);
        } else if (presetExReps.length() == 2) {
            exRepTxtvw.setText("0" + presetExReps);
        } else {
            exRepTxtvw.setText("00" + presetExReps);
        }


        if (!presetExTime.equals("00:00")) {
            long exerciseTimeInMillis = getTimeInMilliseconds(presetExTime);
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
                    database.updatePresetWorkoutTime(workoutTimeString, workoutUid);
                    PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
                    Intent intent = new Intent(getApplicationContext(), PresetRestActivity.class);
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

        String presetExRemainTime = PreferencesManager.getInstance(getApplicationContext()).getCurrentExerciseTimer();
        int timerStatus = PreferencesManager.getInstance(getApplicationContext()).getTimerStatus();

        if (presetExRemainTime != null && !presetExRemainTime.equals("0") && timerStatus == 2) {
            long remainTimeInMillis = Long.parseLong(presetExRemainTime);
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
        Intent intent = new Intent(getApplicationContext(), PresetRestActivity.class);
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
            exTimeTxtvw.setText("00:00");
            startRestScreen();
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
                Intent intent = new Intent(PresetExerciseStartActivity.this, PlayPauseStopActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.top_in, 0);
                return false; // Top to bottom
            }
            return false;
        }
    }
}
