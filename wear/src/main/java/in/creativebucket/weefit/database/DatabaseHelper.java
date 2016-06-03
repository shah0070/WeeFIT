package in.creativebucket.weefit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.creativebucket.weefit.dto.CreateWorkoutData;
import in.creativebucket.weefit.dto.PresetWorkout;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WeeFitDb";

    ///////CREATE WORKOUT TABLE
    private static final String CREATE_WORKOUT_TABLE = "CreateWorkoutTable";
    private static final String CREATE_WORKOUT_ID = "CreateWorkoutId";
    private static final String CREATE_WORKOUT_CATEGORY = "CreateWorkoutCategory";
    private static final String CREATE_WORKOUT_POSITION = "CreateWorkoutPosition";
    private static final String CREATE_WORKOUT_TIME = "WorkoutTime";
    private static final String CREATE_WORKOUT_REPS = "WorkoutReps";
    private static final String CREATE_WORKOUT_SETS = "WorkoutSets";
    private static final String CREATE_WORKOUT_REST = "WorkoutRestTime";
    private static final String CREATE_WORKOUT_CALORY = "WorkoutCalory";


    ///////PRESET WORKOUT TABLE
    private static final String PRESET_EXERCISE_TABLE = "PresetExerciseTable";

    private static final String PRESET_EX_UID = "PresetUId";
    private static final String PRESET_WORKOUT_ID = "PresetExId";
    private static final String PRESET_EX_TIME = "PresetExTime";
    private static final String PRESET_EX_REPS = "PresetExReps";
    private static final String PRESET_EX_SETS = "PresetExSets";
    private static final String PRESET_EX_CALORY = "PresetExCalory";
    private static final String PRESET_EX_REST = "PresetExRestTime";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + CREATE_WORKOUT_TABLE + " ("
                + CREATE_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CREATE_WORKOUT_CATEGORY
                + " TEXT, " + CREATE_WORKOUT_POSITION + " INTEGER, " + CREATE_WORKOUT_TIME + " TEXT, " + CREATE_WORKOUT_SETS + " TEXT, " + CREATE_WORKOUT_REPS + " TEXT, " + CREATE_WORKOUT_REST + " TEXT, " + CREATE_WORKOUT_CALORY + " TEXT)";

        db.execSQL(CREATE_EXERCISE_TABLE);

        String CREATE_PRESET_EXERCISE_TABLE = "CREATE TABLE " + PRESET_EXERCISE_TABLE + " (" + PRESET_EX_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRESET_WORKOUT_ID + " INTEGER, " + PRESET_EX_TIME
                + " TEXT, " + PRESET_EX_REPS + " TEXT, " + PRESET_EX_SETS + " TEXT, " + PRESET_EX_CALORY + " TEXT, " + PRESET_EX_REST + " TEXT )";

        db.execSQL(CREATE_PRESET_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUT_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + PRESET_EXERCISE_TABLE + "");
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete) news + get all
     * news + delete all news
     */

    public void addNewItem(CreateWorkoutData newData) {

        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(CREATE_WORKOUT_CATEGORY, newData.getCategory());
        values.put(CREATE_WORKOUT_POSITION, newData.getPosition());
        values.put(CREATE_WORKOUT_TIME, newData.getWorkoutTime());
        values.put(CREATE_WORKOUT_SETS, newData.getWorkoutSets());
        values.put(CREATE_WORKOUT_REPS, newData.getWorkoutReps());
        values.put(CREATE_WORKOUT_REST, newData.getWorkoutRestTime());
        values.put(CREATE_WORKOUT_CALORY, newData.getWorkoutCalory());

        try {
            db.insert(CREATE_WORKOUT_TABLE, // table
                    null, // nullColumnHack
                    values); // key/value -> keys = column names/ values = column
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

    }


    public CreateWorkoutData getNewsItem(String newsId) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(CREATE_WORKOUT_TABLE, // a. table
                new String[]{CREATE_WORKOUT_ID, CREATE_WORKOUT_CATEGORY, CREATE_WORKOUT_POSITION}, // b. column names
                " " + CREATE_WORKOUT_ID + " = ?", // c. selections
                new String[]{String.valueOf(newsId)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build News object
        CreateWorkoutData createWorkoutData = new CreateWorkoutData();
        createWorkoutData.setWorkoutId(cursor.getInt(0));
        createWorkoutData.setCategory(cursor.getString(1));
        createWorkoutData.setPosition(cursor.getInt(2));
        return createWorkoutData;
    }


    public ArrayList<CreateWorkoutData> getAllCreatedWorkouts() {
        ArrayList<CreateWorkoutData> createWorkoutDatas = new ArrayList<CreateWorkoutData>();

        // 1. build the query
        String query = "SELECT  * FROM " + CREATE_WORKOUT_TABLE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        CreateWorkoutData workoutData = null;
        if (cursor.moveToFirst()) {
            do {
                workoutData = new CreateWorkoutData();
                workoutData.setWorkoutId(cursor.getInt(0));
                workoutData.setCategory(cursor.getString(1));
                workoutData.setPosition(cursor.getInt(2));
                workoutData.setWorkoutTime(cursor.getString(3));
                workoutData.setWorkoutSets(cursor.getString(4));
                workoutData.setWorkoutReps(cursor.getString(5));
                workoutData.setWorkoutRestTime(cursor.getString(6));
                workoutData.setWorkoutCalory(cursor.getString(7));

                createWorkoutDatas.add(workoutData);
            } while (cursor.moveToNext());
        }

        return createWorkoutDatas;
    }

    // Updating  Workout
    public void updateWorkoutInfo(CreateWorkoutData createWorkoutData) {

        String categoryName = createWorkoutData.getCategory();
        int workoutPos = createWorkoutData.getPosition();
        String workoutTime = createWorkoutData.getWorkoutTime();
        String workoutSets = createWorkoutData.getWorkoutSets();
        String workoutReps = createWorkoutData.getWorkoutReps();
        String workoutRestTime = createWorkoutData.getWorkoutRestTime();
        String workoutCalory = createWorkoutData.getWorkoutCalory();

        String query = "UPDATE " + CREATE_WORKOUT_TABLE + " SET " + CREATE_WORKOUT_TIME + "='" + workoutTime + "', " + CREATE_WORKOUT_SETS + "='" + workoutSets + "', " + CREATE_WORKOUT_REPS + "='" + workoutReps + "', " + CREATE_WORKOUT_REST + "='" + workoutRestTime + "', " + CREATE_WORKOUT_CALORY + "='" + workoutCalory + "' WHERE " + CREATE_WORKOUT_CATEGORY + " = '" + categoryName + "' AND " + CREATE_WORKOUT_POSITION + "=" + workoutPos;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }

    }

    public void updateCreatedWorkoutTime(String workoutTime, int workoutId) {

        String query = "UPDATE " + CREATE_WORKOUT_TABLE + " SET " + CREATE_WORKOUT_TIME + "='" + workoutTime + "' WHERE " + CREATE_WORKOUT_ID + " = " + workoutId;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }


    public void updatePresetWorkoutTime(String workoutTime, int presetExUid) {

        String query = "UPDATE " + PRESET_EXERCISE_TABLE + " SET " + PRESET_EX_TIME + "='" + workoutTime + "' WHERE " + PRESET_EX_UID + " = " + presetExUid;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void removeAllCreatedWorkouts() {

        String query = "DELETE FROM " + CREATE_WORKOUT_TABLE + "";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }


    public boolean getIsWorkoutAlreadyAdded(CreateWorkoutData createWorkoutData) {
        String categoryName = createWorkoutData.getCategory();
        int workoutPos = createWorkoutData.getPosition();
        String query = "SELECT * FROM " + CREATE_WORKOUT_TABLE + " WHERE " + CREATE_WORKOUT_CATEGORY + " = '" + categoryName + "' AND " + CREATE_WORKOUT_POSITION + "=" + workoutPos;
        SQLiteDatabase db = this.getWritableDatabase();
        int count;
        try {
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            if (count >= 1)
                return true;
            else
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public void removeCreatedWorkout(CreateWorkoutData createWorkoutData) {
        String categoryName = createWorkoutData.getCategory();
        int workoutPos = createWorkoutData.getPosition();
        String query = "DELETE FROM " + CREATE_WORKOUT_TABLE + " WHERE " + CREATE_WORKOUT_CATEGORY + " = '" + categoryName + "' AND " + CREATE_WORKOUT_POSITION + "=" + workoutPos;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    public int getCountOfExercise(String category) {
        String query = "SELECT * FROM " + CREATE_WORKOUT_TABLE + "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int exerciseCount = cursor.getCount();
        db.close();
        return exerciseCount;
    }

    public void insertPresetWorkoutInDb(ArrayList<PresetWorkout> list) {

        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(PRESET_WORKOUT_ID, list.get(i).getId());
            values.put(PRESET_EX_TIME, list.get(i).getTime());
            values.put(PRESET_EX_REPS, list.get(i).getReps());
            values.put(PRESET_EX_SETS, list.get(i).getSets());
            values.put(PRESET_EX_CALORY, list.get(i).getCalory());
            values.put(PRESET_EX_REST, list.get(i).getRest());

            try {
                db.insert(PRESET_EXERCISE_TABLE, // table
                        null, // nullColumnHack
                        values); // key/value -> keys = column names/ values = column
                // values
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        // 4. close
        db.close();
    }

    public ArrayList<PresetWorkout> getAllPresetWorkout(int workoutId) {
        ArrayList<PresetWorkout> presetWorkoutList = new ArrayList<PresetWorkout>();

        // 1. build the query
        String query = "SELECT  * FROM " + PRESET_EXERCISE_TABLE + " WHERE " + PRESET_WORKOUT_ID + "=" + workoutId;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        PresetWorkout presetWorkout = null;
        if (cursor.moveToFirst()) {
            do {
                presetWorkout = new PresetWorkout();
                presetWorkout.setUniqueId(cursor.getInt(0));
                presetWorkout.setId(cursor.getInt(1));
                presetWorkout.setTime(cursor.getString(2));
                presetWorkout.setReps(cursor.getString(3));
                presetWorkout.setSets(cursor.getString(4));
                presetWorkout.setCalory(cursor.getString(5));
                presetWorkout.setRest(cursor.getString(6));

                presetWorkoutList.add(presetWorkout);
            } while (cursor.moveToNext());
        }

        return presetWorkoutList;
    }

}
