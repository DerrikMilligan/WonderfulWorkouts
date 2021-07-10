package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "user_measurements")
public class Measurement {
    // All the possible measurement types
    public static String weight = "weight";
    public static String biceps = "biceps";
    public static String chest  = "chest";
    public static String thighs = "thighs";
    public static String waist  = "waist";
    public static String hips   = "hips";
    public static String neck   = "neck";
    public static String calves = "calves";

    @PrimaryKey(autoGenerate = true)
    public int measurementId;
    public int userId;
    public String type;
    public float value;
    public LocalDateTime lastUpdated;
}
