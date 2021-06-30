package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "workout_history")
public class WorkoutHistory {
    @PrimaryKey
    public int workoutHistoryId;
    public int workoutId;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
}
