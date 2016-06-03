package in.creativebucket.weefit.dto;

import android.content.Context;

import java.util.ArrayList;

import in.creativebucket.weefit.R;

/**
 * Created by Chandan kumar on 6/28/2015.
 */
public class ExerciseImagesAndData {

    public int[] pressetWorkoutList() {

        int[] mWorkoutResorces;
        mWorkoutResorces = new int[]{
                R.drawable.exercise_one,
                R.drawable.exercise_two,
                R.drawable.exercise_three,
                R.drawable.exercise_four,
                R.drawable.exercise_five,
                R.drawable.exercise_six
        };
        return mWorkoutResorces;
    }

    public int[] presetWorkoutExerciseList(int workoutId) {

        int[] mWorkoutResorces;

        if (workoutId == 1) {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching,
                    R.drawable.stretch_band_bicep_curl,
                    R.drawable.waist_turns,
                    R.drawable.wrist_stretch
            };
        } else if (workoutId == 2) {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir
            };
        } else if (workoutId == 3) {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else if (workoutId == 4) {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else if (workoutId == 5) {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else {
            mWorkoutResorces = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        }
        return mWorkoutResorces;
    }

    public int[] createWorkoutExerciseList(Context context, String categoryName) {

        int[] mGymResources;

        if (categoryName.equals(context.getResources().getString(R.string.category_one))) {
            mGymResources = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching,
                    R.drawable.stretch_band_bicep_curl,
                    R.drawable.waist_turns,
                    R.drawable.wrist_stretch
            };
        } else if (categoryName.equals(context.getResources().getString(R.string.category_two))) {
            mGymResources = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else if (categoryName.equals(context.getResources().getString(R.string.category_three))) {
            mGymResources = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else if (categoryName.equals(context.getResources().getString(R.string.category_four))) {
            mGymResources = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else if (categoryName.equals(context.getResources().getString(R.string.category_five))) {
            mGymResources = new int[]{
                    R.drawable.barrier_jumps,
                    R.drawable.static_wall_sir,
                    R.drawable.stretch_band_fly,
                    R.drawable.stretching
            };
        } else {
            mGymResources = new int[]{};
        }

        return mGymResources;
    }

    public ArrayList<PresetWorkout> getWorkoutExerciseList() {
        ArrayList<PresetWorkout> allExerciseList = new ArrayList<>();
        PresetWorkout presetWorkout = null;
        for (int k = 1; k <= AppConstants.TOATL_PRESET_WORKOUTS; k++) {
            String[][] exerciseInfoArray = getExerciseInfo(k);

            for (int i = 0; i < exerciseInfoArray[0].length; i++) {
                presetWorkout = new PresetWorkout();
                presetWorkout.setId(k);
                for (int j = 0; j < exerciseInfoArray.length; j++) {
                    if (j == 0) {
                        String time = exerciseInfoArray[j][i];
                        presetWorkout.setTime(time);
                    } else if (j == 1) {
                        String reps = exerciseInfoArray[j][i];
                        presetWorkout.setReps(reps);
                    } else if (j == 2) {
                        String sets = exerciseInfoArray[j][i];
                        presetWorkout.setSets(sets);
                    } else if (j == 3) {
                        String calory = exerciseInfoArray[j][i];
                        presetWorkout.setCalory(calory);
                    } else {
                        String rest = exerciseInfoArray[j][i];
                        presetWorkout.setRest(rest);
                    }
                }
                allExerciseList.add(presetWorkout);
            }
        }
        return allExerciseList;
    }

    public String[][] getExerciseInfo(int workoutId) {
        String[][] exerciseArrays;
        String[] timeArray, repsArray, setsArray, caloryArray, restArray;
        if (workoutId == 1) {
            timeArray = new String[]{"00:40", "00:40", "00:40", "00:40", "00:40", "00:40", "00:40"};
            repsArray = new String[]{"000", "000", "000", "000", "000", "000", "000"};
            setsArray = new String[]{"003", "002", "003", "002", "004", "003", "002"};
            caloryArray = new String[]{"30", "40", "30", "50", "20", "40", "30"};
            restArray = new String[]{"01:00", "02:00", "01:00", "02:00", "01:00", "02:00", "01:00"};

        } else if (workoutId == 2) {
            timeArray = new String[]{"00:00", "00:20"};
            repsArray = new String[]{"002", "000"};
            setsArray = new String[]{"002", "003"};
            caloryArray = new String[]{"30", "40"};
            restArray = new String[]{"00:10", "00:15"};

        } else if (workoutId == 3) {
            timeArray = new String[]{"01:00", "02:00", "03:00", "04:00", "02:00", "03:00"};
            repsArray = new String[]{"002", "004", "005", "007", "004", "003"};
            setsArray = new String[]{"002", "004", "005", "003", "005", "008"};
            caloryArray = new String[]{"30", "40", "50", "60", "70", "80",};
            restArray = new String[]{"02:00", "02:00", "04:00", "02:00", "04:00", "02:00"};

        } else if (workoutId == 4) {
            timeArray = new String[]{"01:00", "02:00", "03:00", "04:00", "02:00", "03:00"};
            repsArray = new String[]{"002", "004", "005", "007", "004", "003"};
            setsArray = new String[]{"002", "004", "005", "003", "005", "008"};
            caloryArray = new String[]{"30", "40", "50", "60", "70", "80",};
            restArray = new String[]{"02:00", "02:00", "04:00", "02:00", "04:00", "02:00"};

        } else if (workoutId == 5) {
            timeArray = new String[]{"01:00", "02:00", "03:00", "04:00", "02:00", "03:00"};
            repsArray = new String[]{"002", "004", "005", "007", "004", "003"};
            setsArray = new String[]{"002", "004", "005", "003", "005", "008"};
            caloryArray = new String[]{"30", "40", "50", "60", "70", "80",};
            restArray = new String[]{"02:00", "02:00", "04:00", "02:00", "04:00", "02:00"};

        } else {
            timeArray = new String[]{"01:00", "02:00", "03:00", "04:00", "02:00", "03:00"};
            repsArray = new String[]{"002", "004", "005", "007", "004", "003"};
            setsArray = new String[]{"002", "004", "005", "003", "005", "008"};
            caloryArray = new String[]{"30", "40", "50", "60", "70", "80",};
            restArray = new String[]{"02:00", "02:00", "04:00", "02:00", "04:00", "02:00"};
        }

        exerciseArrays = new String[][]{timeArray, repsArray, setsArray, caloryArray, restArray};
        return exerciseArrays;

    }

}



