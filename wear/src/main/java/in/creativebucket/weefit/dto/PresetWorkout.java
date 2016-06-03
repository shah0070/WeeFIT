package in.creativebucket.weefit.dto;

/**
 * Created by Chandan kumar on 7/4/2015.
 */
public class PresetWorkout {

    public int uniqueId;
    public int workoutId;
    public String time;
    public String reps;
    public String sets;
    public String calory;


    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String rest;

    public int getId() {
        return workoutId;
    }

    public void setId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

}
