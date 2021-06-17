package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {
    @PrimaryKey
    public int workoutId;
    public String name;
    public int userId;
}