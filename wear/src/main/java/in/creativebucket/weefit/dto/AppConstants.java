package in.creativebucket.weefit.dto;

/**
 * Created by Chandan kumar on 6/27/2015.
 */
public interface AppConstants {
    String CATEGORY_NAME = "categoryName";
    String PRESET_EXERCISE = "preset_exercise";
    String CREATE_WORKOUT_EXERCISE = "create_exercise";
    String PRESET_WORKOUT_ID = "workout_id";
    String WORKOUT_POSITION = "workout_pos";
    String OPTION_VALUE = "option_value";
    String WORKOUT_TYPE = "workout_type";
    int WORKOUT_TYPE_PRESET = 1;
    int WORKOUT_TYPE_CREATED = 2;
    String IS_FROM_QUEUE = "is_from_queue";

    boolean FIRST_LAUNCH = true;
    int PRESET_EX_NUMBER = 6;
    int TOATL_PRESET_WORKOUTS = 6;
    String PRESET_EX_TIME = "preset_ex_time";
    String PRESET_EX_REPS = "preset_ex_reps";
    String PRESET_EX_SET = "preset_ex_sets";
    String PRESET_EX_CALORY = "preset_ex_calory";
    String PRESET_EX_REST = "preset_ex_rest";
    String PRESET_EXERCISE_POS = "exercise_pos";
    String PRESET_EX_UID = "ex_uid";


    String CREATED_EXERCISE_IMAGE = "created_exercise_image";
    String CREATED_WORKOUT_ID = "created_workout_id";
    String CREATED_WORKOUT_POS = "created_workout_pos";
    String CREATED_EX_TIME = "created_ex_time";
    String CREATED_EX_REPS = "created_ex_reps";
    String CREATED_EX_SETS = "created_ex_sets";
    String CREATED_EX_CALORY = "created_ex_calory";
    String CREATED_EX_REST = "created_ex_rest";

    String IS_FIRST_EXERCISE = "is_first_exercise";


    int SWIPE_MIN_DISTANCE = 120;
    int SWIPE_THRESHOLD_VELOCITY = 200;

    int TIMER_PAUSE = 1;
    int TIMER_PLAY = 2;

    int TIME_SELECT = 1;
    int REPS_SELECT = 2;
    int REST_SELECT = 3;

}

