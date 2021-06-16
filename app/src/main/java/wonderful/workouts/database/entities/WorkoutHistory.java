package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "workout_history")
public class WorkoutHistory {
    @PrimaryKey
    public int id;
    public int workoutId;
    public Date startTime;
    public Date endTime;
}
