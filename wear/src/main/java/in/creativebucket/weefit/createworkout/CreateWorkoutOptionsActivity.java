package in.creativebucket.weefit.createworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.dto.AppConstants;

/**
 * Created by Chandan kumar on 8/1/2015.
 */
public class CreateWorkoutOptionsActivity extends Activity implements AppConstants {

    private ImageView imgVwTimeBased, imgVwRepsBased;
    private String categoryName;
    private int workoutPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout_options);
        categoryName = getIntent().getStringExtra(CATEGORY_NAME);
        workoutPos = getIntent().getIntExtra(WORKOUT_POSITION, -1);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    public void setUIonScreen(WatchViewStub stub) {
        imgVwTimeBased = (ImageView) stub.findViewById(R.id.time_based);
        imgVwRepsBased = (ImageView) stub.findViewById(R.id.reps_based);

        imgVwTimeBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateWorkoutTimeRepsActivity.class);
                intent.putExtra(CATEGORY_NAME, categoryName);
                intent.putExtra(WORKOUT_POSITION, workoutPos);
                intent.putExtra(OPTION_VALUE, TIME_SELECT);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

        imgVwRepsBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateWorkoutTimeRepsActivity.class);
                intent.putExtra(CATEGORY_NAME, categoryName);
                intent.putExtra(WORKOUT_POSITION, workoutPos);
                intent.putExtra(OPTION_VALUE, REPS_SELECT);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

    }


}
