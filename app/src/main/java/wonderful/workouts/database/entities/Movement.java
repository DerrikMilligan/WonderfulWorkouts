package wonderful.workouts.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "movements",
    indices = { @Index(value = "movementId") }
)
public class Movement {
    // Some static strings for the Movement.type to use. Could be an enum class but whatever...
    @Ignore public static final String RepsAndWeight = "reps_and_weight";
    @Ignore public static final String Reps = "reps";
    @Ignore public static final String Timed = "timed";

    @PrimaryKey(autoGenerate = true)
    public int movementId;
    public String name;
    public String url;
    public String type;
}