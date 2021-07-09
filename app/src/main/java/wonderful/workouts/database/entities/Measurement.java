package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_measurements")
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    public int measurementId;
    public int userId;
    public String type;
    public float value;
}
