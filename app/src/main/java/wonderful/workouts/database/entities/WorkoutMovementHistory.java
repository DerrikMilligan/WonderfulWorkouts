package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_movement_history")
public class WorkoutMovementHistory {
    @PrimaryKey
    public int id;
    public int workoutHistoryId;
    public int movementId;
    public float weight;
    public float reps;
    public float duration;
}