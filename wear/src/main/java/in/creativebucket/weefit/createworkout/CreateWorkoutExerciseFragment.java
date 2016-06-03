package in.creativebucket.weefit.createworkout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.database.DatabaseHelper;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CreateWorkoutData;
import in.creativebucket.weefit.dto.ExerciseImagesAndData;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/5/2015.
 */

public class CreateWorkoutExerciseFragment extends Fragment implements AppConstants {

    public Bundle bundle;
    private int gymExImage;
    private ImageView exerciseImage, startWhistleImageVw, selectedImage;
    private TextView totalCountTxtvw;
    private DatabaseHelper database;
    private ArrayList<CreateWorkoutData> workoutList;
    private int workoutId, workoutPosition;
    private int[] exerciseImages;
    private String categoryName;
    private boolean is_from_queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        gymExImage = bundle.getInt(CREATE_WORKOUT_EXERCISE);
        categoryName = bundle.getString(CATEGORY_NAME);
        workoutPosition = bundle.getInt(CREATED_WORKOUT_POS);
        is_from_queue = bundle.getBoolean(IS_FROM_QUEUE, false);

        database = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_workout_fragment, container, false);
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
        totalCountTxtvw = (TextView) stub.findViewById(R.id.total_count);
        exerciseImage = (ImageView) stub.findViewById(R.id.exercise_picture);
        exerciseImage.setBackgroundResource(gymExImage);
        startWhistleImageVw = (ImageView) stub.findViewById(R.id.start_workouts);
        selectedImage = (ImageView) stub.findViewById(R.id.selected_icon);

        int totalCount = database.getCountOfExercise(categoryName);
        totalCountTxtvw.setText("" + totalCount);

        final CreateWorkoutData createWorkoutData = new CreateWorkoutData();
        createWorkoutData.setCategory(categoryName);
        createWorkoutData.setPosition(workoutPosition);
        createWorkoutData.setWorkoutTime(getString(R.string.default_time));
        createWorkoutData.setWorkoutSets(getString(R.string.default_sets));
        createWorkoutData.setWorkoutReps(getString(R.string.default_reps));
        createWorkoutData.setWorkoutRestTime(getString(R.string.default_rest));
        createWorkoutData.setWorkoutCalory(getString(R.string.default_calory));

        boolean isAlreadyAdded = database.getIsWorkoutAlreadyAdded(createWorkoutData);

        if (isAlreadyAdded && !is_from_queue) {
            selectedImage.setVisibility(View.VISIBLE);
        } else {
            selectedImage.setVisibility(View.GONE);
        }

        if (!is_from_queue) {
            exerciseImage.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {

                    boolean isAlreadyAdded = database.getIsWorkoutAlreadyAdded(createWorkoutData);
                    String message = "";

                    if (!isAlreadyAdded) {
                        message = getActivity().getResources().getString(R.string.added);
                        database.addNewItem(createWorkoutData);
                        Intent intent = new Intent(getActivity(), CreateWorkoutOptionsActivity.class);
                        intent.putExtra(CATEGORY_NAME, categoryName);
                        intent.putExtra(WORKOUT_POSITION, workoutPosition);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    } else {
                        selectedImage.setVisibility(View.GONE);
                        message = getActivity().getResources().getString(R.string.removed);
                        database.removeCreatedWorkout(createWorkoutData);
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    int totalCountVal = database.getCountOfExercise(categoryName);
                    totalCountTxtvw.setText("" + totalCountVal);
                    return false;
                }
            });
        } else {
            totalCountTxtvw.setVisibility(ViewGroup.GONE);
        }

        startWhistleImageVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreatedWorkouts();
            }
        });
    }

    public void startCreatedWorkouts() {
        ArrayList<CreateWorkoutData> workoutList = database.getAllCreatedWorkouts();
        int getNextWorkoutPos = PreferencesManager.getInstance(getActivity()).getNextCreatedWorkoutPos();

        if (workoutList.size() > 0) {
            CreateWorkoutData workoutData = workoutList.get(getNextWorkoutPos);

            exerciseImages = new ExerciseImagesAndData().createWorkoutExerciseList(getActivity(), workoutData.getCategory());
            PreferencesManager.getInstance(getActivity()).setCurrentExerciseTimer(null);
            PreferencesManager.getInstance(getActivity()).setTimerStatus(0);
            PreferencesManager.getInstance(getActivity()).setIsEnablePause(true);
            PreferencesManager.getInstance(getActivity()).setCurrentSetNumber(Integer.parseInt(workoutData.getWorkoutSets()));
            PreferencesManager.getInstance(getActivity()).setIsAllowMessage(true);

            Intent intent = new Intent(getActivity(), CreatedExerciseStartActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt(CREATED_EXERCISE_IMAGE, exerciseImages[workoutData.getPosition()]);
            bundle.putInt(CREATED_WORKOUT_ID, workoutData.getWorkoutId());
            bundle.putInt(CREATED_WORKOUT_POS, 0);
            bundle.putString(CREATED_EX_TIME, workoutData.getWorkoutTime());
            bundle.putString(CREATED_EX_REPS, workoutData.getWorkoutReps());
            bundle.putString(CREATED_EX_SETS, workoutData.getWorkoutSets());
            bundle.putString(CREATED_EX_CALORY, workoutData.getWorkoutCalory());
            bundle.putString(CREATED_EX_REST, workoutData.getWorkoutRestTime());
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().finish();
            getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            Toast.makeText(getActivity(), "Please schedule exercise before start", Toast.LENGTH_SHORT).show();
        }
    }
}
