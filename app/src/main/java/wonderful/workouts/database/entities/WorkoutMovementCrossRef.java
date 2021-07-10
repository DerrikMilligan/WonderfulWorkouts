package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName   = "workout_movements",
    primaryKeys = {"workoutId", "movementId"},
    indices     = {
        @Index(value = "workoutId"),
        @Index(value = "movementId"),
        @Index(value = {"workoutId", "movementId"})
    }
)
public class WorkoutMovementCrossRef {
    public int workoutId;
    public int movementId;
}

