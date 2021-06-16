package wonderful.workouts.database.joiners;

import androidx.room.Relation;
import androidx.room.Embedded;

import java.util.List;

import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.database.entities.User;

public class UserWithMeasurements {
    @Embedded
    public User user;

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    public List<Measurement> measurements;
}