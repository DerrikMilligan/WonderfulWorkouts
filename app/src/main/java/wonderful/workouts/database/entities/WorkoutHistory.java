package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "workout_history")
public class WorkoutHistory {
    @Ignore
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("L/d/u");

    @PrimaryKey(autoGenerate = true)
    public int workoutHistoryId;
    public int workoutId;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
}
