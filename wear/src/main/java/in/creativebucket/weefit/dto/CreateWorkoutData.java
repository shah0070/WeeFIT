package in.creativebucket.weefit.dto;

/**
 * Created by Chandan kumar on 6/28/2015.
 */
public class CreateWorkoutData {


    int workoutId;
    String category;
    int position;
    String workoutTime;
    String workoutReps;
    String workoutSets;
    String workoutRestTime;
    String workoutCalory;

    public String getWorkoutCalory() {
        return workoutCalory;
    }

    public void setWorkoutCalory(String workoutCalory) {
        this.workoutCalory = workoutCalory;
    }

    public String getWorkoutTime() {
        return workoutTime;
    }

    public void setWorkoutTime(String workoutTime) {
        this.workoutTime = workoutTime;
    }


    public String getWorkoutReps() {
        return workoutReps;
    }

    public void setWorkoutReps(String workoutReps) {
        this.workoutReps = workoutReps;
    }

    public String getWorkoutSets() {
        return workoutSets;
    }

    public void setWorkoutSets(String workoutSets) {
        this.workoutSets = workoutSets;
    }

    public String getWorkoutRestTime() {
        return workoutRestTime;
    }

    public void setWorkoutRestTime(String workoutRestTime) {
        this.workoutRestTime = workoutRestTime;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
