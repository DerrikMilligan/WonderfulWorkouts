package wonderful.workouts.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movements")
public class Movement {
    @PrimaryKey
    public int movementId;
    public String name;
    public String url;
    public String type;
}