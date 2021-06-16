package wonderful.workouts.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_measurements")
public class Measurement {
    @PrimaryKey
    public int id;
    public int userId;
    public String type;
    public float value;
}
