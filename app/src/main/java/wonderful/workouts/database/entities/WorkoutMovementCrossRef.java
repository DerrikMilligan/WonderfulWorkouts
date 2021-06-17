package wonderful.workouts.database.entities;

import androidx.room.Entity;

@Entity(
    tableName = "workout_movements",
    primaryKeys = {"workoutId", "movementId"}
)
public class WorkoutMovementCrossRef {
    public int workoutId;
    public int movementId;
}

