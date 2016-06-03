package in.creativebucket.weefit.createworkout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CreateWorkoutData;

/**
 * Created by Chandan kumar on 8/1/2015.
 */
public class CreateWorkoutTimeRepsActivity extends Activity implements AppConstants {

    private String categoryName;
    private int workoutPos, optionValue;
    private LinearLayout inputSetsOrRestMinsLayout, inputRepsOrRestSecondsLayout, inputFirstLayout, inputSecondLayout, inputThirdLayout;
    private RelativeLayout inputTimeSetsLayout, inputRepsSetsLayout;
    private TextView txtVwTitle, txtVwRepsOrSec, txtVwSetsOrMins;
    private ImageView imgSelectIcon;
    private DatabaseHelper database;
    private int workoutMins, workoutSeconds, workoutSets, workoutReps, workoutRestMins, workoutRestSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout_time_reps_activity);
        categoryName = getIntent().getStringExtra(CATEGORY_NAME);
        workoutPos = getIntent().getIntExtra(WORKOUT_POSITION, -1);
        optionValue = getIntent().getIntExtra(OPTION_VALUE, 0);
        database = new DatabaseHelper(getApplicationContext());

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    public void setUIonScreen(WatchViewStub stub) {
        txtVwTitle = (TextView) stub.findViewById(R.id.title);

        inputTimeSetsLayout = (RelativeLayout) stub.findViewById(R.id.input_time_sets);
        inputRepsSetsLayout = (RelativeLayout) stub.findViewById(R.id.input_reps_sets);

        inputFirstLayout = (LinearLayout) stub.findViewById(R.id.input_one);
        inputSecondLayout = (LinearLayout) stub.findViewById(R.id.input_two);
        inputThirdLayout = (LinearLayout) stub.findViewById(R.id.input_three);

        inputSetsOrRestMinsLayout = (LinearLayout) stub.findViewById(R.id.input_sets_mins);
        inputRepsOrRestSecondsLayout = (LinearLayout) stub.findViewById(R.id.input_reps_seconds);

        txtVwRepsOrSec = (TextView) stub.findViewById(R.id.reps_or_second);
        txtVwSetsOrMins = (TextView) stub.findViewById(R.id.sets_or_mins);

        imgSelectIcon = (ImageView) stub.findViewById(R.id.select_image);
        imgSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionValue == 1 || optionValue == 2) {
                    Intent intent = getIntent();
                    intent.putExtra(CATEGORY_NAME, categoryName);
                    intent.putExtra(WORKOUT_POSITION, workoutPos);
                    intent.putExtra(OPTION_VALUE, REST_SELECT);
                    onCreate(null);
                } else if (optionValue == 3) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    CreateWorkoutData createWorkoutData = new CreateWorkoutData();
                    createWorkoutData.setCategory(categoryName);
                    createWorkoutData.setPosition(workoutPos);
                    createWorkoutData.setWorkoutTime(workoutMins + ":" + workoutSeconds);
                    createWorkoutData.setWorkoutSets("" + workoutSets);
                    createWorkoutData.setWorkoutReps("" + workoutReps);
                    createWorkoutData.setWorkoutRestTime(workoutRestMins + ":" + workoutRestSeconds);
                    createWorkoutData.setWorkoutCalory(getResources().getString(R.string.default_calory));
                    database.updateWorkoutInfo(createWorkoutData);

                    Intent intent = new Intent(CreateWorkoutTimeRepsActivity.this, CreateExerciseSelectionActivity.class);
                    intent.putExtra(CATEGORY_NAME, categoryName);
                    intent.putExtra(IS_FROM_QUEUE, false);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                }
            }
        });
        setInputDataForLayouts();
    }

    public void setInputDataForLayouts() {

        if (optionValue == TIME_SELECT) {

            inputTimeSetsLayout.setVisibility(View.VISIBLE);
            inputRepsSetsLayout.setVisibility(View.GONE);


            txtVwTitle.setText(getResources().getString(R.string.time_sets));

            for (int i = 0; i <= 60; i++) {

                /////For First column
                final TextView txtVwFirst = new TextView(this);
                if (i < 10) {
                    txtVwFirst.setText("0" + i);
                } else {
                    txtVwFirst.setText("" + i);
                }
                txtVwFirst.setId(100 + i);
                txtVwFirst.setGravity(Gravity.CENTER);
                txtVwFirst.setTextSize(35.0f);
                txtVwFirst.setTextColor(Color.parseColor("#000000"));
                txtVwFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwFirst.setTextColor(Color.parseColor("#00ff00"));
                        workoutMins = Integer.parseInt(txtVwFirst.getText().toString());
                        int id = txtVwFirst.getId() - 100;
                        for (int j = 0; j < inputFirstLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputFirstLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 100)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputFirstLayout.addView(txtVwFirst);


                /////////// For Third column
                final TextView txtVwThird = new TextView(this);
                if (i < 10) {
                    txtVwThird.setText("0" + i);
                } else {
                    txtVwThird.setText("" + i);
                }
                txtVwThird.setId(300 + i);
                txtVwThird.setTextSize(35.0f);
                txtVwThird.setGravity(Gravity.CENTER);
                txtVwThird.setTextColor(Color.parseColor("#000000"));
                txtVwThird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwThird.setTextColor(Color.parseColor("#00ff00"));
                        workoutSets = Integer.parseInt(txtVwThird.getText().toString());
                        int id = txtVwThird.getId() - 300;
                        for (int j = 0; j < inputThirdLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputThirdLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 300)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputThirdLayout.addView(txtVwThird);
            }

            for (int j = 0; j <= 12; j++) {
                ////// For second column
                final TextView txtVwSecond = new TextView(this);
                if (j <= 1) {
                    txtVwSecond.setText("0" + 5 * j);
                } else {
                    txtVwSecond.setText("" + 5 * j);
                }
                txtVwSecond.setId(200 + 5 * j);
                txtVwSecond.setTextSize(60.0f);
                txtVwSecond.setGravity(Gravity.CENTER);
                txtVwSecond.setTextColor(Color.parseColor("#000000"));
                txtVwSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwSecond.setTextColor(Color.parseColor("#00ff00"));
                        workoutSeconds = Integer.parseInt(txtVwSecond.getText().toString());
                        int id = txtVwSecond.getId() - 200;
                        for (int j = 0; j < inputSecondLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputSecondLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 200)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputSecondLayout.addView(txtVwSecond);
            }

        } else if (optionValue == REPS_SELECT) {


            inputTimeSetsLayout.setVisibility(View.GONE);
            inputRepsSetsLayout.setVisibility(View.VISIBLE);
            txtVwRepsOrSec.setText("Reps");
            txtVwSetsOrMins.setText("Sets");

            txtVwTitle.setText(getResources().getString(R.string.reps_sets));

            for (int i = 0; i <= 60; i++) {

                /////For sets column
                final TextView txtVwFirst = new TextView(this);
                if (i < 10) {
                    txtVwFirst.setText("0" + i);
                } else {
                    txtVwFirst.setText("" + i);
                }
                txtVwFirst.setId(400 + i);
                txtVwFirst.setGravity(Gravity.CENTER);
                txtVwFirst.setTextSize(35.0f);
                txtVwFirst.setTextColor(Color.parseColor("#000000"));
                txtVwFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwFirst.setTextColor(Color.parseColor("#00ff00"));
                        workoutSets = Integer.parseInt(txtVwFirst.getText().toString());
                        int id = txtVwFirst.getId() - 400;
                        for (int j = 0; j < inputSetsOrRestMinsLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputSetsOrRestMinsLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 400)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputSetsOrRestMinsLayout.addView(txtVwFirst);

                ////// For reps column
                final TextView txtVwSecond = new TextView(this);
                if (i < 10) {
                    txtVwSecond.setText("0" + i);
                } else {
                    txtVwSecond.setText("" + i);
                }
                txtVwSecond.setId(500 + i);
                txtVwSecond.setTextSize(60.0f);
                txtVwSecond.setGravity(Gravity.CENTER);
                txtVwSecond.setTextColor(Color.parseColor("#000000"));
                txtVwSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwSecond.setTextColor(Color.parseColor("#00ff00"));
                        workoutReps = Integer.parseInt(txtVwSecond.getText().toString());
                        int id = txtVwSecond.getId() - 500;
                        for (int j = 0; j < inputRepsOrRestSecondsLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputRepsOrRestSecondsLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 500)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputRepsOrRestSecondsLayout.addView(txtVwSecond);
            }
        } else if (optionValue == REST_SELECT) {

            inputTimeSetsLayout.setVisibility(View.GONE);
            inputRepsSetsLayout.setVisibility(View.VISIBLE);

            txtVwTitle.setText(getResources().getString(R.string.rest_text));
            txtVwRepsOrSec.setText("Seconds");
            txtVwSetsOrMins.setText("Mins");

            for (int i = 0; i <= 60; i++) {

                /////For Mins column
                final TextView txtVwFirst = new TextView(this);
                if (i < 10) {
                    txtVwFirst.setText("0" + i);
                } else {
                    txtVwFirst.setText("" + i);
                }
                txtVwFirst.setId(600 + i);
                txtVwFirst.setGravity(Gravity.CENTER);
                txtVwFirst.setTextSize(35.0f);
                txtVwFirst.setTextColor(Color.parseColor("#000000"));
                txtVwFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwFirst.setTextColor(Color.parseColor("#00ff00"));
                        workoutRestMins = Integer.parseInt(txtVwFirst.getText().toString());
                        int id = txtVwFirst.getId() - 600;
                        for (int j = 0; j < inputSetsOrRestMinsLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputSetsOrRestMinsLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 600)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputSetsOrRestMinsLayout.addView(txtVwFirst);
            }

            for (int j = 0; j <= 12; j++) {
                ////// For second column
                final TextView txtVwSecond = new TextView(this);
                if (j <= 1) {
                    txtVwSecond.setText("0" + 5 * j);
                } else {
                    txtVwSecond.setText("" + 5 * j);
                }
                txtVwSecond.setId(700 + 5 * j);
                txtVwSecond.setTextSize(60.0f);
                txtVwSecond.setGravity(Gravity.CENTER);
                txtVwSecond.setTextColor(Color.parseColor("#000000"));
                txtVwSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtVwSecond.setTextColor(Color.parseColor("#00ff00"));
                        workoutRestSeconds = Integer.parseInt(txtVwSecond.getText().toString());
                        int id = txtVwSecond.getId() - 700;
                        for (int j = 0; j < inputRepsOrRestSecondsLayout.getChildCount(); j++) {
                            TextView txtVw = (TextView) inputRepsOrRestSecondsLayout.getChildAt(j);
                            if (id != (txtVw.getId() - 700)) {
                                txtVw.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                    }
                });
                inputRepsOrRestSecondsLayout.addView(txtVwSecond);
            }
        }
    }
}
