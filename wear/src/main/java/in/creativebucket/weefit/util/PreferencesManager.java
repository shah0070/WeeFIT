package in.creativebucket.weefit.util;

import android.content.Context;
import android.content.SharedPreferences;

import in.creativebucket.weefit.WeeFitApplication;

/**
 * Created by Chandan kumar.
 */
public class PreferencesManager {
    private static final String SHARED_PREFS = "weefit_shared_prefs";
    public static final String IS_FIRST_LAUNCH = "first_launch";
    public static final String CURRENT_EXERCISE_TIMER = "exercise_timer";
    public static final String CURRENT_REST_TIMER = "rest_timer";
    public static final String TIMER_STATUS = "timer_status";
    public static final String IS_ENABLE_PAUSE = "is_enable_pause";
    public static final String CURR_SET_NUMBER = "curr_set_number";
    public static final String CURR_REP_NUMBER = "curr_rep_number";
    public static final String NEXT_WORKOUT_POS = "next_workout_pos";
    public static final String NEXT_EXERICSE_POS = "next_exercise_pos";
    public static final String IS_ALLOW_MSG = "is_allow_msg";


    private SharedPreferences mSharedPrefs;
    private static PreferencesManager sInstance;
    private SharedPreferences.Editor mEditor;
    private Context mCtx;

    public synchronized static PreferencesManager getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(ctx);
        }
        return sInstance;
    }

    private PreferencesManager(Context ctx) {
        mSharedPrefs = ctx.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
        mCtx = ctx.getApplicationContext();
    }

    public synchronized SharedPreferences getSharedPref() {
        return mSharedPrefs;
    }

    public void setIsFirstLaunch(final boolean isFirstLaunch) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).commit();
            }
        });
    }

    public boolean getIsFirstLaunch() {
        return mSharedPrefs.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void setCurrentExerciseTimer(final String exerciseTimer) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putString(CURRENT_EXERCISE_TIMER, exerciseTimer).commit();
            }
        });
    }

    public String getCurrentExerciseTimer() {
        return mSharedPrefs.getString(CURRENT_EXERCISE_TIMER, null);
    }

    public void setCurrentRestTimer(final String restTimer) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putString(CURRENT_REST_TIMER, restTimer).commit();
            }
        });
    }

    public String getCurrentRestTimer() {
        return mSharedPrefs.getString(CURRENT_REST_TIMER, null);
    }

    public void setTimerStatus(final int timerStatus) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putInt(TIMER_STATUS, timerStatus).commit();
            }
        });
    }

    public int getTimerStatus() {
        return mSharedPrefs.getInt(TIMER_STATUS, 0);
    }

    public void setIsEnablePause(final boolean isEnablePause) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putBoolean(IS_ENABLE_PAUSE, isEnablePause).commit();
            }
        });
    }

    public boolean isEnablePause() {
        return mSharedPrefs.getBoolean(IS_ENABLE_PAUSE, true);
    }

    public void setCurrentSetNumber(final int setNumber) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putInt(CURR_SET_NUMBER, setNumber).commit();
            }
        });
    }

    public int getCurrentSetNumber() {
        return mSharedPrefs.getInt(CURR_SET_NUMBER, 0);
    }

//    public void setCurrentRepNumber(final int setRepNumber) {
//        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
//        app.getPreferenceExecutor().submit(new Runnable() {
//            @Override
//            public void run() {
//                mEditor.putInt(CURR_REP_NUMBER, setRepNumber).commit();
//            }
//        });
//    }
//
//    public int getCurrentRepNumber() {
//        return mSharedPrefs.getInt(CURR_REP_NUMBER, 0);
//    }

    public void setNextExercisePos(final int nextExercisePos) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putInt(NEXT_EXERICSE_POS, nextExercisePos).commit();
            }
        });
    }

    public int getNextExercisePos() {
        return mSharedPrefs.getInt(NEXT_EXERICSE_POS, 0);
    }

    public void setNextCreatedWorkoutPos(final int nextWorkoutPos) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putInt(NEXT_WORKOUT_POS, nextWorkoutPos).commit();
            }
        });
    }

    public int getNextCreatedWorkoutPos() {
        return mSharedPrefs.getInt(NEXT_WORKOUT_POS, 0);
    }

    public void setIsAllowMessage(final boolean isAllowMessage) {
        WeeFitApplication app = WeeFitApplication.getApplication(mCtx);
        app.getPreferenceExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mEditor.putBoolean(IS_ALLOW_MSG, isAllowMessage).commit();
            }
        });
    }

    public boolean getIsAllowMessage() {
        return mSharedPrefs.getBoolean(IS_ALLOW_MSG, false);
    }

}
